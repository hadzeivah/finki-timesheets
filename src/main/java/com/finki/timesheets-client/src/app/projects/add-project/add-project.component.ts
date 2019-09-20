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
  isLinear = false;
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
      partnerOrganisation: ['', Validators.required],
      university: [null, Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });

    if (data) {
      this.title = "Edit projects";
      this.updateFormFields();
    }
  }

  ngOnInit() {
    this.addPositionsGroup = this.fb.group({
      positions: this.fb.array([])
    });
    this.positionService.findPositionByProject(1).subscribe();
    this.positionService.findPositions().subscribe(positions => {
      this.seedData = positions;
      this.seedPositionFormArray();
    });
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
        positionType: position.name
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
    this.projectPosition = new ProjectPositionDto(this.project,this.positionsFormArray.value);
    this.dialogRef.close(this.projectPosition);
  }

  updateFormFields() {

    this.addProjectForm.patchValue(
      {
        name: this.data.name,
        projectNumber: this.data.projectNumber,
        partnerOrganisation: this.data.partnerOrganisation,
        university: this.data.university,
        startDate: this.data.startDate,
        endDate: this.data.endDate
      })
  }

  getFormControl() {
    return this.fb.control(null);
  }
}

