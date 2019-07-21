import {Component, Input, OnInit} from '@angular/core';
import {Member} from "../model/Member";
import {BehaviorSubject} from "rxjs";
import {Datasource} from "../timesheet/timesheet.component";
import {MemberService} from "../services/member.service";

@Component({
  selector: 'members-list',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.css']
})
export class MembersComponent implements OnInit {


  @Input()
  set membersOnProject(value: Member[]) {
    this.members = value;
    this.subject.next(this.members);
  }

  members: Member[];
  subject = new BehaviorSubject(this.members);
  dataSource = new Datasource(this.subject.asObservable());
  displayedColumns: string[] = ['fullName', 'actions'];

  constructor(private membersService: MemberService) {
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
}
