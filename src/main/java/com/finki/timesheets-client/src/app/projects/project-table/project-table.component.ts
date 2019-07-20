import {Component, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {ProjectService} from "../../services/project.service";
import {AddProjectComponent} from "../add-project/add-project.component";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {BehaviorSubject} from "rxjs";
import {Datasource} from "../../timesheet/timesheet.component";

@Component({
  selector: 'app-project-table',
  templateUrl: './project-table.component.html',
  styleUrls: ['./project-table.component.css']
})
export class ProjectTableComponent implements OnInit {
  projects: Project[];
  subject = new BehaviorSubject(this.projects);
  dataSource = new Datasource(this.subject.asObservable());
  displayedColumns: string[] = ['projectName', 'projectNumber', 'partnerOrganisation', 'startDate', 'endDate', 'actions'];

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

  addProjectDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '250px';

    const dialogRef = this.dialog.open(AddProjectComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      const newProject: Project = Object.assign({},result);
      this.projectService.addProject(newProject).subscribe(project =>
      {
        this.projects.push(project.result);
        this.subject.next(this.projects);
      }, err => console.log('HTTP Error', err),
      );
    });

  }

  editUser(element: any) {

  }

  deleteProject(project: Project): void {

    this.projectService.deleteProject(project.id)
      .subscribe(data => {
        this.projects = this.projects.filter(p => p !== project);
      });
  }

}
