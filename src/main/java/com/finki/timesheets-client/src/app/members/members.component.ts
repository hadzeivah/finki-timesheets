import {Component, OnInit, ViewChild} from '@angular/core';
import {MemberService} from "../services/member.service";
import {MatDialog, MatDialogConfig, MatTableDataSource} from "@angular/material";
import {AddMemberComponent} from "./add-member/add-member.component";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MemberProjectsDto} from "../model/MemberProjectsDto";
import {TimesheetService} from "../services/timesheet.service";
import {map} from "rxjs/operators";
import {ConfirmDialogComponent} from "../confirm-dialog/confirm-dialog.component";
import {NotificationService} from "../services/notification.service";


@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {

  members: MemberProjectsDto[];
  dataSource = new MatTableDataSource<MemberProjectsDto>();
  displayedColumns: string[] = ['fullName', 'embg', 'transactionAccount', 'positionType', 'projects', 'actions'];
  isLoading: Boolean = true;
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private membersService: MemberService,
              private timesheetService: TimesheetService,
              private notificationService: NotificationService,
              public dialog: MatDialog) {
    this.loadMembers();
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    // this function is needed because MemberProjectsDto contains Objects
    this.dataSource.filterPredicate = (data: any, filter) => {
      const dataStr = JSON.stringify(data).toLowerCase();
      return dataStr.indexOf(filter) != -1;
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  loadMembers() {
    this.membersService.findMembersDetails().subscribe(data => {
      this.dataSource.data = data;
      this.members = data;
      this.isLoading = false;
    });
  }

  deleteMember(memberProjectsDto: MemberProjectsDto) {

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: false
    });

    dialogRef.componentInstance.confirmMessage = "Are you sure you want to delete?";

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // do confirmation actions
        this.membersService.deleteMember(memberProjectsDto.member.id)
          .subscribe(data => {
            this.dataSource.data = this.dataSource.data.filter(p => p.member.id !== memberProjectsDto.member.id);
          });
      }
    });
  }

  addMemberDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';

    const dialogRef = this.dialog.open(AddMemberComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(member => {
      if (member) {
        this.membersService.addMember(member)
          .subscribe(() => {
            this.loadMembers();
          }, error => this.notificationService.openSnackBar(error));
      }
    });
  }

  editMemberDialog(editedMember: MemberProjectsDto) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';

    dialogConfig.data = {
      editedMember: editedMember,
    };

    const dialogRef = this.dialog.open(AddMemberComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(member => {
      member.id = editedMember.member.id;
      this.membersService.updateMember(member)
        .subscribe(() => {
          this.loadMembers();
        })
    })
  }

  deleteTimesheet(memberId: number, projectId: number, member: MemberProjectsDto) {
    this.timesheetService.deleteTimesheet(memberId, projectId).subscribe(result => {
      if (result.status == 200) {
        member.projectPosition = member.projectPosition.filter(p => p.projectId !== projectId);
        member.projectPosition = [].concat(member.projectPosition);
      } else {
        this.notificationService.openSnackBar(result.message);
      }
    });
  }
}
