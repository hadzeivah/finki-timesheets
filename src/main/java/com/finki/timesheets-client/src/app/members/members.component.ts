import {Component, Input, OnInit} from '@angular/core';
import {Member} from "../model/Member";
import {MemberService} from "../services/member.service";
import {MatDialog, MatDialogConfig, MatTableDataSource} from "@angular/material";
import {AddMemberComponent} from "./add-member/add-member.component";

@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['fullName', 'embg', 'position', 'transactionAccount', 'actions'];

  @Input()
  set membersOnProject(value: Member[]) {
    this.dataSource.data = value;
  }

  constructor(private membersService: MemberService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    if (!this.membersOnProject)
      this.loadMembers();
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
    dialogConfig.width = '250px';

    if (editedMember) {
      dialogConfig.data = editedMember;
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
          this.membersService.addMember(member)
            .subscribe(() => {
              this.loadMembers();
            });
        }
      }
    });
  }
}
