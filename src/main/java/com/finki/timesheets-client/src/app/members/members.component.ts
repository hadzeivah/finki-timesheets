import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {Member} from "../model/Member";
import {MemberService} from "../services/member.service";
import {MatDialog, MatDialogConfig, MatTableDataSource} from "@angular/material";
import {AddMemberComponent} from "./add-member/add-member.component";
import {Project} from "../model/Project";
import {isNotNullOrUndefined} from "codelyzer/util/isNotNullOrUndefined";
import {MatSidenav} from "@angular/material/sidenav";

@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['fullName', 'embg', 'positionType', 'transactionAccount', 'actions'];
  selectedProject: Project;

  @Input()
  set project(value: Project) {
    this.selectedProject = value;
    this.displayedColumns = ['fullName', 'positionType', 'actions'];
    this.dataSource.data = value.members;
  }

  constructor(private membersService: MemberService,
              public dialog: MatDialog) {
    if (!this.isProjectSelectedMode())
      this.loadMembers();
  }

  ngOnInit() {
    console.log("ng on init")
  }

  isProjectSelectedMode() {
    return isNotNullOrUndefined(this.selectedProject);
  }

  loadMembers() {
    this.membersService.findMembers()
      .subscribe(data => {
          this.dataSource.data = data.result;
        }, err => console.log('HTTP Error', err),
      );
  }

  deleteMember(member: Member) {
    this.membersService.deleteMember(member.id)
      .subscribe(data => {
        this.dataSource.data = this.dataSource.data.filter(p => p !== member);
      });
  }

  addMemberDialog(editedMember?: Member) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';

    dialogConfig.data = {
      showProjectsFiled: !this.isProjectSelectedMode()
    };
    if (editedMember) {
      dialogConfig.data = {
        editedMember: editedMember,
        showProjectsFiled: !this.isProjectSelectedMode()
      }
    }

    const dialogRef = this.dialog.open(AddMemberComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(member => {
      if (member) {
        if (editedMember) {
          member.id = editedMember.id;
          this.membersService.updateMember(member)
            .subscribe(() => {
              this.loadMembers();
            })
        } else {

          if (isNotNullOrUndefined(this.selectedProject))
            member.projects.push(this.selectedProject);
          this.membersService.addMember(member)
            .subscribe(() => {
              this.loadMembers();
            });
        }
      }
    });
  }
}
