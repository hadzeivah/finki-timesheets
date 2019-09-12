import {Component, OnInit} from '@angular/core';
import {Project} from '../../model/Project';
import {ProjectService} from '../../services/project.service';
import {ActivatedRoute, Router} from "@angular/router";
import {Member} from "../../model/Member";
import {AddProjectComponent} from "../add-project/add-project.component";
import {MatDialog} from "@angular/material";
import {AddMemberComponent} from "../../members/add-member/add-member.component";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  selectedProject: Project;
  selectedMember: Member;
  searchProject: string;
  searchMember: string;

  constructor(private projectService: ProjectService,
              public dialog: MatDialog) {
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
  }

  onSelectMember(member: Member) {
    this.selectedMember = member;
  }


  addMemberDialog() {
    const dialogRef = this.dialog.open(AddMemberComponent, {
      width: '250px',
    });

    dialogRef.afterClosed().subscribe(result => {
    });

  }
}
