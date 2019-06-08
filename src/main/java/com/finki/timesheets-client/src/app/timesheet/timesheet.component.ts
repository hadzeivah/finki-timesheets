import {Component, OnInit} from '@angular/core';
import {TimesheetService} from '../services/timesheet.service';
import {Timesheet} from '../model/Timesheet';
import {Item} from '../model/Item';
import {ItemService} from '../services/item.service';
import {BehaviorSubject, Observable} from 'rxjs';
import {DataSource} from '@angular/cdk/table';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-timesheet',
  templateUrl: './timesheet.component.html',
  styleUrls: ['./timesheet.component.css']
})
export class TimesheetComponent implements OnInit {

  displayedColumns: string[] = ['startDate', 'endDate', 'hours', 'taskDescription', 'intellectualOutput', 'actions'];
  values: Item[] = [];
  timesheet: Timesheet;
  loaded = false;
  subject = new BehaviorSubject(this.values);
  dataSource = new Datasource(this.subject.asObservable());
  form: FormGroup;
  itemsForm: FormGroup;
  itemList: FormArray;
  projectId: number;
  memberId: number;

  constructor(private timesheetService: TimesheetService,
              private itemService: ItemService,
              private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router) {

    this.form = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      hours: ['', Validators.required],
      taskDescription: ['', Validators.required],
      intellectualOutput: ['', Validators.required],
    });

    this.itemsForm = this.fb.group({
      items: this.fb.array([this.createItem()])
    });
    this.itemList = this.itemsForm.get('items') as FormArray;
  }

  get itemsFormGroup() {
    return this.itemsForm.get('items') as FormArray;
  }

  getItemsFormGroup(index): FormGroup {
    this.itemList = this.itemsFormGroup;
    return this.itemList.controls[index] as FormGroup;
  }

  createItem(): FormGroup {
    return this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      hours: ['', Validators.required],
      taskDescription: ['', Validators.required],
      intellectualOutput: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.projectId = +params['projectId'];
      this.memberId = +params['memberId'];
      this.getTimesheet();
    });
  }

  getTimesheet() {

    this.timesheetService.findTimesheet(this.projectId, this.memberId)
      .subscribe(data => {
        this.timesheet = data;
        this.getItems(this.timesheet.id);
      }, err => console.log('HTTP Error', err));
  }

  private setItemsFormArray(item: Item) {
    return this.fb.group({
      startDate: [item.startDate],
      endDate: [item.endDate],
      hours: [item.hours],
      taskDescription: [item.taskDescription],
      intellectualOutput: [item.intellectualOutput]
    });
  }

  getItems(id) {
    this.itemService.findItems(id)
      .subscribe(data => {
          if (data !== null) {
            this.values = data;
          } else {
            this.values = null;
          }
          this.loaded = true;
          this.setItemsForm();
          this.subject.next(this.values);
        }, err => console.log('HTTP Error', err),
      );

  }

  getTotalCost() {
    if (this.values) {
      return this.values.map(t => t.hours).reduce((acc, value) => acc + value, 0);
    }
  }

  deleteItem(i: any, id: any) {
    this.itemService.deleteItem(id).subscribe(() => {
        const foundIndex = this.values.findIndex(x => x.id === id);
        this.values.splice(foundIndex, 1);
        this.subject.next(this.values);
      }
    );
  }

  addNew() {
    const source = this.subject.getValue();
  }

  confirmEditCreate(element, index): void {
    element.editing = false;
    const updatedItem = {
      id: element.id,
      timesheetId: element.timesheet.id,
      startDate: this.getItemsFormGroup(index).controls['startDate'].value,
      endDate: this.getItemsFormGroup(index).controls['endDate'].value,
      hours: this.getItemsFormGroup(index).controls['hours'].value,
      taskDescription: this.getItemsFormGroup(index).controls['taskDescription'].value,
      intellectualOutput: this.getItemsFormGroup(index).controls['intellectualOutput'].value,
      editing: false
    };
    this.itemService.updateItem(updatedItem).subscribe(item => {
      this.getItems(this.timesheet.id);
    }, err => console.log('HTTP Error', err));
  }

  startEdit(i, element): void {
    element.editing = true;
    this.updateItemFormValues(element, i);
  }

  onSubmit() {
    const newItem: Item = Object.assign({}, this.form.value);
    newItem.timesheetId = this.timesheet.id;
    this.itemService.addItem(newItem).subscribe(item => {
      this.values.push(item.result);
      const itemCtrl = this.itemsForm.get('items') as FormArray;
      itemCtrl.push(this.setItemsFormArray(newItem));
      this.subject.next(this.values);
    }, err => console.log('HTTP Error', err));
  }

  private setItemsForm() {
    const itemCtrl = this.itemsForm.get('items') as FormArray;
    if (this.values !== null) {
      this.values.forEach((item) => {
        itemCtrl.push(this.setItemsFormArray(item));
      });
    }
  }

  private updateItemFormValues(item: Item, index: number) {
    const itemCtrl = this.itemsForm.get('items') as FormArray;
    itemCtrl.controls[index].patchValue({
      startDate: item.startDate,
      endDate: item.endDate,
      hours: item.hours,
      taskDescription: item.taskDescription,
      intellectualOutput: item.intellectualOutput
    });

  }
}

export class Datasource extends DataSource<any> {


  constructor(private _list$: Observable<any[]>) {
    super();
  }

  connect(): Observable<any[]> {
    return this._list$;
  }

  disconnect() {
  }
}

