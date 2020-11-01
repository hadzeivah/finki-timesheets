import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {University} from "../../model/University";
import {UniversityService} from "../../services/university.service";
import {PositionService} from "../../services/position.service";
import {Position} from "../../model/Position";
import {ProjectDetailsDto} from "../../model/ProjectDetailsDto";
import {WorkPackagesService} from "../../services/work-packages.service";
import {WorkPackage} from "../../model/WorkPackage";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  universities: University[];
  addProjectsGroup: FormGroup;
  addPositionsGroup: FormGroup;
  addWorkPackageGroup: FormGroup;
  editedProject: Project;
  title: string = "Add projects";
  seedData: Position[];
  projectPosition: ProjectDetailsDto;
  workPackages: WorkPackage[];
  positionToDelete: any = [];

  constructor(
    public dialogRef: MatDialogRef<AddProjectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private fb: FormBuilder,
    private universityService: UniversityService,
    private positionService: PositionService,
    private workPackagesService: WorkPackagesService,
    private notificationService: NotificationService) {

    this.getUniversities();
    this.getWorkPackages();

    this.addProjectsGroup = this.fb.group({
      name: ['', Validators.required],
      projectNumber: ['', Validators.required],
      estimatedBudget: ['', Validators.required],
      university: [null, Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });

    this.addPositionsGroup = this.fb.group({
      positions: this.fb.array([])
    });

    this.addWorkPackageGroup = this.fb.group({
      workPackage: [null, Validators.required]
    });

    if (data) {
      this.title = "Edit projects";
      this.editedProject = data;
      this.updateFormFields();
    }
  }

  ngOnInit() {

    if (this.editedProject) {
      this.positionService.findSalaryGroupedByPosition(this.editedProject.id)
        .subscribe(positionSalary => {
          this.seedData = Object.keys(positionSalary).map(function (key) {
            return new Position(key, positionSalary[key]);
          });
          this.seedPositionFormArray();
        });

    } else {
      this.positionService.findPositions().subscribe(positions => {
        this.seedData = positions;
        this.seedPositionFormArray();
      });
    }
  }

  get positionsFormArray() {
    return (<FormArray>this.addPositionsGroup.get('positions'));
  }

  get workPackage() {
    return this.addWorkPackageGroup.get('workPackage');
  }

  createPositionGroup() {
    return this.fb.group({
      positionType: '',
      salary: ''
    });
  }

  addPositionToPositionsFormArray() {
    this.positionsFormArray.push(this.createPositionGroup());
  }

  removePositionToPositionsFormArray(index) {
    this.positionToDelete.push(this.positionsFormArray.value[index]);
    this.positionsFormArray.removeAt(index);
  }

  seedPositionFormArray() {
    this.seedData.forEach(position => {
      const formGroup = this.createPositionGroup();
      formGroup.addControl('salary', this.getFormControl());
      let positionTypeObj = {
        positionType: position.name,
        salary: position.salary != null ? position.salary : 0
      };
      formGroup.patchValue(positionTypeObj);
      this.positionsFormArray.push(formGroup);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getUniversities() {
    this.universityService.findUniversities()
      .subscribe(data => {
          this.universities = data.result;
        }, err => this.notificationService.openSnackBar(err.message)
      );

  }

  getWorkPackages() {
    this.workPackagesService.findWorkPackages()
      .subscribe(data => {
          this.workPackages = data;
        }, err => this.notificationService.openSnackBar(err.message)
      );

  }

  save() {
    this.projectPosition = new ProjectDetailsDto(this.addProjectsGroup.value, this.positionsFormArray.value, this.positionToDelete, this.workPackage.value);
    this.projectPosition.changed = this.addProjectsGroup.dirty || this.addWorkPackageGroup.dirty || this.addPositionsGroup.dirty;
    this.dialogRef.close(this.projectPosition);
  }

  updateFormFields() {
    this.addProjectsGroup.patchValue(
      {
        name: this.editedProject.name,
        projectNumber: this.editedProject.projectNumber,
        estimatedBudget: this.editedProject.estimatedBudget,
        university: this.editedProject.university,
        startDate: this.editedProject.startDate,
        endDate: this.editedProject.endDate
      });
    this.addWorkPackageGroup.patchValue({
      workPackage: this.editedProject.workPackage
    });
  }

  getFormControl() {
    return this.fb.control(null);
  }

  compareUniversityObjects(university1: any, university2: any) {
    return university1 && university2 && university1.id == university2.id;
  }

  compareWorkPackageObjects(workPackage1: any, workPackage2: any) {
    return workPackage1 && workPackage2 && workPackage1.id == workPackage2.id;
  }
}

