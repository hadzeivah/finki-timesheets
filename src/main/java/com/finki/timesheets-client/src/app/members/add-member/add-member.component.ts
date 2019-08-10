import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent implements OnInit {

  projects: Project[];
  addMemberForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<AddMemberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project[],
    private fb: FormBuilder) {
    this.projects = data;
    this.addMemberForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      project: ['', Validators.required]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit(): void {
  }

}
