import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {Project} from "../../model/Project";
import {ProjectService} from "../../services/project.service";

@Component({
  selector: 'app-document-page',
  templateUrl: './document-page.component.html',
  styleUrls: ['./document-page.component.css']
})
export class DocumentPageComponent implements OnInit {

  projectCtrl = new FormControl('', Validators.required);
  projects: Project[];
  selectedProject: Project;

  constructor(private projectService: ProjectService) {
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

  onProjectSelected(project: Project) {
    this.selectedProject = project;
  }

}
