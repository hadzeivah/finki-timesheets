import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {University} from "../../model/University";
import {UniversityService} from "../../services/university.service";
import {PositionService} from "../../services/position.service";
import {Position} from "../../model/Position";
import {ProjectPositionsDto} from "../../model/ProjectPositionsDto";
import {WorkPackagesService} from "../../services/work-packages.service";
import {WorkPackage} from "../../model/WorkPackage";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  universities: University[];
  addProjectForm: FormGroup;
  addPositionsGroup: FormGroup;
  editedProject: Project;
  title: string = "Add projects";
  seedData: Position[];
  projectPosition: ProjectPositionsDto;
  workPackages: WorkPackage[];

  constructor(
    public dialogRef: MatDialogRef<AddProjectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private fb: FormBuilder,
    private universityService: UniversityService,
    private positionService: PositionService,
    private workPackagesService: WorkPackagesService) {

    this.getUniversities();
    this.getWorkPackages();

    this.addProjectForm = this.fb.group({
      name: ['', Validators.required],
      projectNumber: ['', Validators.required],
      estimatedBudget: ['', Validators.required],
      university: [null, Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });

    if (data) {
      this.title = "Edit projects";
      this.editedProject = data;
      this.updateFormFields();
    }
  }

  ngOnInit() {
    this.addPositionsGroup = this.fb.group({
      positions: this.fb.array([])
    });

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
        }, err => console.log('HTTP Error', err),
      );

  }

  getWorkPackages() {
    this.workPackagesService.findWorkPackages()
      .subscribe(data => {
          this.workPackages = data;
        }, err => console.log('HTTP Error', err),
      );

  }

  save() {
    this.projectPosition = new ProjectPositionsDto(this.addProjectForm.value, this.positionsFormArray.value);
    this.dialogRef.close(this.projectPosition);
  }

  updateFormFields() {
    this.addProjectForm.patchValue(
      {
        name: this.editedProject.name,
        projectNumber: this.editedProject.projectNumber,
        estimatedBudget: this.editedProject.estimatedBudget,
        university: this.editedProject.university,
        startDate: this.editedProject.startDate,
        endDate: this.editedProject.endDate
      })
  }

  getFormControl() {
    return this.fb.control(null);
  }

  compareUniversityObjects(university1: any, university2: any) {
    return university1 && university2 && university1.id == university2.id;
  }
}

