import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemberService} from "../../services/member.service";
import {PositionType} from "../../model/PositionType";
import {Project} from "../../model/Project";
import {Member} from "../../model/Member";

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent implements OnInit {

  addMemberForm: FormGroup;
  positions: PositionType[];
  projects: Project[];
  editedMember: Member;

  constructor(
    public dialogRef: MatDialogRef<AddMemberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private memberService: MemberService) {

    this.buildForm();

    if (data['editedMember']) {
      this.editedMember = data['editedMember'];
      this.updateFormFields();
    }
    this.memberService.getMemberTypes()
      .subscribe(positions =>
        this.positions = positions);
  }

  private buildForm() {
    this.addMemberForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      embg: ['', Validators.required],
      transactionAccount: ['', Validators.required]
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  ngOnInit(): void {
  }

  onSaveClick() {
    this.dialogRef.close(this.addMemberForm.value);
  }

  updateFormFields() {
    this.addMemberForm.patchValue(
      {
        firstName: this.editedMember.firstName,
        lastName: this.editedMember.lastName,
        embg: this.editedMember.embg,
        transactionAccount: this.editedMember.transactionAccount,
      })
  }
}
