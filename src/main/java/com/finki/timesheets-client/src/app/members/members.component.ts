import {Component, Input, OnInit} from '@angular/core';
import {Member} from "../model/Member";
import {BehaviorSubject} from "rxjs";
import {Datasource} from "../timesheet/timesheet.component";
import {MemberService} from "../services/member.service";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {AddProjectComponent} from "../projects/add-project/add-project.component";
import {Project} from "../model/Project";
import {AddMemberComponent} from "./add-member/add-member.component";

@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  members: Member[];
  subject = new BehaviorSubject(this.members);
  dataSource = new Datasource(this.subject.asObservable());
  displayedColumns: string[] = ['fullName', 'actions'];

  @Input()
  set membersOnProject(value: Member[]) {
    this.members = value;
    this.subject.next(this.members);
  }

  constructor(private membersService: MemberService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    if (!this.membersOnProject)
      this.getMembers();
  }

  getMembers() {
    this.membersService.findMembers()
      .subscribe(data => {
          this.members = data.result;
          this.subject.next(this.members);
        }, err => console.log('HTTP Error', err),
      );

  }

  deleteMember(member: Member) {
    this.membersService.deleteMember(member.id)
      .subscribe(data => {
        this.members = this.members.filter(p => p !== member);
        this.subject.next(this.members);
      });
  }

  editUser(element: any) {

  }

  addMemberDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '250px';

    const dialogRef = this.dialog.open(AddMemberComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      const newMember: Member = Object.assign({}, result);
      this.membersService.addMember(newMember).subscribe(member => {
          this.members.push(member.result);
          this.subject.next(this.members);
        }, err => console.log('HTTP Error', err),
      );
    });

  }
}
