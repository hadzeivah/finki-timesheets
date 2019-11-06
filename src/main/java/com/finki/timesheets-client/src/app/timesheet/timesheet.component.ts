import {Component, Input, OnInit} from '@angular/core';
import {TimesheetService} from '../services/timesheet.service';
import {Timesheet} from '../model/Timesheet';
import {Item} from '../model/Item';
import {Position} from '../model/Position';
import {ItemService} from '../services/item.service';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatCalendarCellCssClasses, MatTableDataSource} from "@angular/material";
import {PositionService} from "../services/position.service";
import {HolidayService} from "../services/holiday.service";
import {ProjectService} from "../services/project.service";
import {Project} from "../model/Project";
import {Member} from "../model/Member";
import {map} from "rxjs/operators";
import {isNotNullOrUndefined} from "codelyzer/util/isNotNullOrUndefined";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {UploadService} from "../services/upload.service";

@Component({
  selector: 'app-timesheet',
  templateUrl: './timesheet.component.html',
  styleUrls: ['./timesheet.component.css']
})
export class TimesheetComponent implements OnInit {

  arrayBuffer: any;
  file: File;
  displayedColumns: string[] = ['startDate', 'endDate', 'hours', 'taskDescription', 'intellectualOutput', 'actions'];
  items: Item[] = [];
  timesheet: Timesheet;
  _project: Project;
  dataSource = new MatTableDataSource();
  form: FormGroup;
  itemsForm: FormGroup;
  itemList: FormArray;
  datesToHighlight = [];
  _fromDate: Date;
  _toDate: Date;
  _member: Member;
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  memberPosition: Position;


  @Input()
  set member(member: Member) {
    this._member = member;
    if (isNotNullOrUndefined(member)) {
      this.getTimesheet();
    }
  }

  get member() {
    return this._member;
  }


  @Input()
  set project(project: Project) {
    this._project = project;
  }

  get project() {
    return this._project;
  }

  @Input()
  set fromDate(date: Date) {
    this._fromDate = date;
  }

  get fromDate() {
    return this._fromDate;
  }

  @Input()
  set toDate(date: Date) {
    this._toDate = date;
    this.applyFilter();
  }

  get toDate() {
    return this._toDate;
  }

  weekendFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  };


  constructor(private timesheetService: TimesheetService,
              private positionsService: PositionService,
              private holidayService: HolidayService,
              private itemService: ItemService,
              private projectService: ProjectService,
              private uploadService: UploadService,
              private fb: FormBuilder) {

    this.dataSource.filterPredicate = (data: Item, filter) => {
      if (this.fromDate && this.toDate) {
        let fromDate = new Date(this.fromDate);
        let toDate = new Date(this.toDate);
        let itemStartDate = new Date(data.startDate);
        let itemEndDate = new Date(data.endDate);

        return itemStartDate.getTime() >= fromDate.getTime() && itemEndDate.getTime() <= toDate.getTime();
      }
      return true;
    };

  }

  applyFilter() {
    this.dataSource.filter = '' + Math.random();
  }

  ngOnInit() {
    this.buildInsertForm();
    this.holidayService.findHolidays().subscribe(holidays => {
      this.datesToHighlight = holidays;
      }
    );
    this.positionsService.findPositionById(this.timesheet.projectPosition.id).subscribe(position =>
      this.memberPosition = position
    )
  }

  buildInsertForm() {
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


  getTimesheet() {

    this.timesheetService.findTimesheet(this.project.id, this.member.id)
      .subscribe(data => {
        this.timesheet = data;
        this.dataSource.data = this.timesheet.items;
        this.items = this.timesheet.items;
        this.setItemsForm();
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

  getTotalTimeSpent() {
    if (this.items) {
      return this.items.map(t => t.hours).reduce((acc, value) => acc + value, 0);
    }
  }

  getTotalTimeSpentInDays() {
    return Math.round((this.getTotalTimeSpent() / 8) * 10) / 10;
  }

  deleteItem(i: any, id: any) {
    this.itemService.deleteItem(id).subscribe(() => {
      const foundIndex = this.items.findIndex(x => x.id === id);
      this.items.splice(foundIndex, 1);
      this.dataSource.data = this.items;
      }
    );
  }

  confirmEditCreate(element, index): void {
    element.editing = false;
    const updatedItem = {
      id: element.id,
      timesheetId: this.timesheet.id,
      startDate: this.getItemsFormGroup(index).controls['startDate'].value,
      endDate: this.getItemsFormGroup(index).controls['endDate'].value,
      hours: this.getItemsFormGroup(index).controls['hours'].value,
      taskDescription: this.getItemsFormGroup(index).controls['taskDescription'].value,
      intellectualOutput: this.getItemsFormGroup(index).controls['intellectualOutput'].value,
      editing: false
    };
    this.itemService.updateItem(updatedItem).subscribe(item => {
      const foundIndex = this.items.findIndex(x => x.id === item.result.id);
      this.items [foundIndex] = item.result;
      this.dataSource.data = [...this.items];
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
      this.items.push(item.result);
      this.dataSource.data = this.items;
      const itemCtrl = this.itemsForm.get('items') as FormArray;
      itemCtrl.push(this.setItemsFormArray(newItem));
    }, err => console.log('HTTP Error', err));
  }

  private setItemsForm() {
    const itemCtrl = this.itemsForm.get('items') as FormArray;
    if (this.items !== null) {
      this.items.forEach((item) => {
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

  dateClass() {
    return (date: Date): MatCalendarCellCssClasses => {
      const highlightDate = this.datesToHighlight
        .map(strDate => new Date(strDate))
        .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth());

      return highlightDate ? 'special-date' : '';
    };
  }

  public getTotalCost(): number {
    let member = this.timesheet != null ? this.timesheet.member : null;
    return member != null ? this.timesheet.projectPosition.salary * this.getTotalTimeSpent() / 8 : 0;
  }

  uploadFile(files: FileList) {

    if (files.length == 0) {
      console.log("No file selected!");
      return
    }
    let file: File = files[0];
    this.uploadService.uploadFile(file, 1)
      .subscribe(
        event => {
          if (event.type == HttpEventType.UploadProgress) {
            const percentDone = Math.round(100 * event.loaded / event.total);
            console.log(`File is ${percentDone}% loaded.`);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely loaded!');
          }
        },
        (err) => {
          console.log("Upload Error:", err);
        }, () => {
          console.log("Upload done");
        }
      )
  }

  selectFile(event) {
    this.uploadFile(event.target.files);
  }
}

