import {Component, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {Member} from "../../model/Member";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-timesheet-page',
  templateUrl: './timesheet-page.component.html',
  styleUrls: ['./timesheet-page.component.css']
})
export class TimesheetPageComponent implements OnInit {
  projects: Project[] = [];
  members: Member[] = [];
  selectedProject: Project;
  selectedMember: Member;
  projectCtrl = new FormControl('', Validators.required);
  memberCtrl = new FormControl('', Validators.required);
  filterForm = new FormGroup({
    fromDate: new FormControl(),
    toDate: new FormControl(),
  });
  memberId: number;
  projectId: number;


  constructor(private projectService: ProjectService,
              public router: Router) {

  }

  ngOnInit() {
    this.getProjects();

  }

  get getFromDate() {
    return this.filterForm.get('fromDate').value;
  }

  get getToDate() {
    return this.filterForm.get('toDate').value;
  }

  getProjects() {
    this.projectService.findProjects()
      .subscribe(data => {
          this.projects = data.result;
        }, err => console.log('HTTP Error', err),
      );

  }

  onSelectProject(project: Project) {
    this.selectedProject = project;
    this.members = this.selectedProject.timesheets.map(a => a.member)
  }

  onSelectMember(member: Member) {
    this.selectedMember = member;
  }
}
