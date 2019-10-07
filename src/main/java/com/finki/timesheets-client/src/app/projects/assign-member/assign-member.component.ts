import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PositionType} from "../../model/PositionType";
import {Member} from "../../model/Member";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MemberService} from "../../services/member.service";

@Component({
  selector: 'app-assign-member',
  templateUrl: './assign-member.component.html',
  styleUrls: ['./assign-member.component.css']
})
export class AssignMemberComponent implements OnInit {
  assignMemberForm: FormGroup;
  positions: PositionType[];
  members: Member[];

  constructor(
    public dialogRef: MatDialogRef<AssignMemberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private memberService: MemberService) {

    this.buildForm();

    this.memberService.getMemberTypes()
      .subscribe(positions =>
        this.positions = positions);

    this.memberService.findMembers()
      .subscribe(members =>
        this.members = members.result);

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
