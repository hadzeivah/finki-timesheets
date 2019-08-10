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
      universityId: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });

     if(data){
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
    this.dialogRef.close(this.addProjectForm.value);
  }

  updateFormFields(){

    this.addProjectForm.patchValue(
      {
        name: this.data.name,
        projectNumber: this.data.projectNumber,
        partnerOrganisation: this.data.partnerOrganisation,
        universityId: this.data.universityId,
        startDate: this.data.startDate,
        endDate: this.data.endDate
      })
  }
}
