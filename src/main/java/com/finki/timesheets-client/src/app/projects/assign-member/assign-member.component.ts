import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PositionType} from "../../model/PositionType";
import {Member} from "../../model/Member";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MemberService} from "../../services/member.service";
import {Project} from "../../model/Project";
import * as _ from 'lodash';

@Component({
  selector: 'app-assign-member',
  templateUrl: './assign-member.component.html',
  styleUrls: ['./assign-member.component.css']
})
export class AssignMemberComponent implements OnInit {
  assignMemberForm: FormGroup;
  positions: PositionType[];
  members: Member[];
  selectedProject: Project;
  assignedMembers: Member[];
  notAssignedMembers: Member[];

  constructor(
    public dialogRef: MatDialogRef<AssignMemberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Project,
    private fb: FormBuilder,
    private memberService: MemberService) {

    this.buildForm();
    this.selectedProject = this.data["project"];
    this.memberService.getMemberTypes()
      .subscribe(positions =>
        this.positions = positions);


    if (this.selectedProject) {
      this.members = this.data["members"];
      this.assignedMembers = this.selectedProject.timesheets.map(a => a.member);
      this.notAssignedMembers = _.differenceBy(this.members, this.assignedMembers, 'id');
    }

  }

  ngOnInit() {
  }

  private buildForm() {
    this.assignMemberForm = this.fb.group({
      member: ['', Validators.required],
      positionType: ['', Validators.required]
    });
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.dialogRef.close(this.assignMemberForm.value);
  }
}
