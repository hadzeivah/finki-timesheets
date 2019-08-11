import {Component, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {ProjectService} from "../../services/project.service";
import {AddProjectComponent} from "../add-project/add-project.component";
import {MatDialog, MatDialogConfig, MatTableDataSource} from "@angular/material";
import {Member} from "../../model/Member";

@Component({
  selector: 'app-project-table',
  templateUrl: './project-table.component.html',
  styleUrls: ['./project-table.component.css']
})
export class ProjectTableComponent implements OnInit {
  projects: Project[];
  selectedProject: Project;
  members: Member[];
  dataSource = new MatTableDataSource();
  displayedColumns: string[] = ['projectName', 'projectNumber', 'partnerOrganisation','startDate', 'endDate', 'actions'];

  constructor(private projectService: ProjectService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.loadProjects();
  }

  loadProjects() {
    this.projectService.findProjects()
      .subscribe(data => {
          this.dataSource.data = data.result;
        }, err => console.log('HTTP Error', err),
      );

  }

  addProject() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '250px';

    const dialogRef = this.dialog.open(AddProjectComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(project => {
      if (project) {
        this.projectService.addProject(project).subscribe(() => {
            this.loadProjects();
          }, err => console.log('HTTP Error', err),
        )
      }
    });

  }
  editProject(project : Project) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '250px';
    dialogConfig.data = project;

    const dialogRef = this.dialog.open(AddProjectComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(updatedProject => {
      if (updatedProject) {
        updatedProject.id =  project.id;
        this.projectService.updateProject(updatedProject).subscribe(() => {
            this.loadProjects();
          }, err => console.log('HTTP Error', err),
        )
      }
    });

  }

  deleteProject(project: Project): void {

    this.projectService.deleteProject(project.id)
      .subscribe(data => {
        this.dataSource.data = this.dataSource.data.filter(p => p !== project);
      });
  }

  displayMembers(project: Project) {
    this.selectedProject = project;
  }
}
