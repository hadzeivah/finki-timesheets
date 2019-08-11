import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {University} from "../../model/University";
import {UniversityService} from "../../services/university.service";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  universities: University[];
  addProjectForm: FormGroup;
  project: Project;
  title: string = "Add projects";

  constructor(
    public dialogRef: MatDialogRef<AddProjectComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private fb: FormBuilder,
    private universityService: UniversityService) {

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
    this.dialogRef.close(this.project);
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
}

