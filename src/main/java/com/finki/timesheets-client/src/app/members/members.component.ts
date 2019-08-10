import {Component, Input, OnInit} from '@angular/core';
import {Member} from "../model/Member";
import {BehaviorSubject} from "rxjs";
import {Datasource} from "../timesheet/timesheet.component";
import {MemberService} from "../services/member.service";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {AddMemberComponent} from "./add-member/add-member.component";
import {ProjectService} from "../services/project.service";
import {Project} from "../model/Project";

@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  members: Member[];
  projects: Project[];
  subject = new BehaviorSubject(this.members);
  dataSource = new Datasource(this.subject.asObservable());
  displayedColumns: string[] = ['fullName', 'actions'];

  @Input()
  set membersOnProject(value: Member[]) {
    this.members = value;
    this.subject.next(this.members);
  }

  constructor(private membersService: MemberService,
              private projectService: ProjectService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    if (!this.membersOnProject)
      this.getMembers();
    this.getProjects();
  }

  getProjects() {
    this.projectService.findProjects()
      .subscribe(data => {
          this.projects = data.result;
        }, err => console.log('HTTP Error', err),
      );

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
    dialogConfig.data = this.projects;
    const dialogRef = this.dialog.open(AddMemberComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.membersService.addMember(result).subscribe(member => {
          this.members.push(member.result);
          this.subject.next(this.members);
        }, err => console.log('HTTP Error', err),
      );
    });

  }
}
