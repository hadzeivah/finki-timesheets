import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {University} from "../../model/University";
import {UniversityService} from "../../services/university.service";
import {PositionService} from "../../services/position.service";
import {Position} from "../../model/Position";
import {ProjectPositionDto} from "../../model/ProjectPositionDto";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  universities: University[];
  addProjectForm: FormGroup;
  addPositionsGroup: FormGroup;
  project: Project;
  title: string = "Add projects";
  seedData: Position[];
  projectPosition: ProjectPositionDto;

  constructor(
    public dialogRef: MatDialogRef<AddProjectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private fb: FormBuilder,
    private universityService: UniversityService,
    private positionService: PositionService) {

    this.getUniversities();
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
      this.project = data;
      this.updateFormFields();
    }
  }

  ngOnInit() {
    this.addPositionsGroup = this.fb.group({
      positions: this.fb.array([])
    });

    if (this.project) {
      this.positionService.findSalaryGroupedByPosition(this.project.id)
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

  save() {
    this.project = <Project>this.addProjectForm.value;
    this.projectPosition = new ProjectPositionDto(this.project, this.positionsFormArray.value);
    this.dialogRef.close(this.projectPosition);
  }

  updateFormFields() {
    this.addProjectForm.patchValue(
      {
        name: this.project.name,
        projectNumber: this.project.projectNumber,
        estimatedBudget: this.project.estimatedBudget,
        university: this.project.university,
        startDate: this.project.startDate,
        endDate: this.project.endDate
      })
  }

  getFormControl() {
    return this.fb.control(null);
  }

  compareUniversityObjects(university1: any, university2: any) {
    return university1 && university2 && university1.id == university2.id;
  }
}

