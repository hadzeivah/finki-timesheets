import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Project} from "../../model/Project";
import {MemberService} from "../../services/member.service";
import {Member} from "../../model/Member";

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css']
})
export class AddProjectComponent {

  members: Member[];

  constructor(
    public dialogRef: MatDialogRef<AddProjectComponent>,
    private memberService: MemberService,
    @Inject(MAT_DIALOG_DATA) public data: Project) {
    this.getMembers();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getMembers() {
    this.memberService.findMembers()
      .subscribe(data => {
          this.members = data.result;
        }, err => console.log('HTTP Error', err),
      );

  }
}
