import {Component, OnInit} from '@angular/core';
import {Project} from '../model/Project';
import {ProjectService} from '../services/project.service';
import {ActivatedRoute, Router} from "@angular/router";
import {Member} from "../model/Member";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  dataSource: Project[] = [];
  selectedProject: Project;
  selectedMember: Member;

  constructor(private projectService: ProjectService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.getProjects();
  }

  getProjects() {
    this.projectService.findProjects()
      .subscribe(data => {
          this.dataSource = data.result;
        }, err => console.log('HTTP Error', err),
      );

  }

  onSelectProject(project: Project) {
    this.selectedProject = project;
  }

  onSelectMember(member: Member) {
    this.selectedMember = member;
  }
}
