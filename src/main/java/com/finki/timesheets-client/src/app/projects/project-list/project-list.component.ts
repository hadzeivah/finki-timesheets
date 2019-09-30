import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/Project';
import {ProjectService} from '../../services/project.service';
import {Router} from "@angular/router";
import {Member} from "../../model/Member";
import {MatDialog} from "@angular/material";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  members: Member[] = [];
  selectedProject: Project;
  selectedMember: Member;
  projectCtrl = new FormControl('', Validators.required);
  memberCtrl = new FormControl('', Validators.required);

  constructor(private projectService: ProjectService,
              public dialog: MatDialog,
              public router: Router) {
  }

  ngOnInit() {
    this.getProjects();
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
    this.router.navigate(['/timesheets/timesheet/project', this.selectedProject.id, 'member', this.selectedMember.id]);
  }

}
