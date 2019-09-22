import {Component, OnInit, ViewChild} from '@angular/core';
import {Project} from "../../model/Project";
import {ProjectService} from "../../services/project.service";
import {AddProjectComponent} from "../add-project/add-project.component";
import {MatDialog, MatDialogConfig, MatTableDataSource} from "@angular/material";
import {Member} from "../../model/Member";
import {PositionService} from "../../services/position.service";
import {MatSidenav} from "@angular/material/sidenav";

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
  displayedColumns: string[] = ['projectName', 'projectNumber', 'partnerOrganisation', 'startDate', 'endDate', 'actions'];
  @ViewChild('drawer') public drawer: MatSidenav;

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

  addProjectDialog() {

    const dialogRef = this.dialog.open(AddProjectComponent, {
      autoFocus: true,
      width: '600'
    });

    dialogRef.afterClosed().subscribe(projectPosition => {
      if (projectPosition) {

        this.projectService.addProject(projectPosition).subscribe(() => {
            this.loadProjects();
          }
        );
      }
    });
  }

  editProjectDialog(editedProject: Project) {

    const dialogRef = this.dialog.open(AddProjectComponent, {
      autoFocus: true,
      width: '600',
      data: editedProject,
    });

    dialogRef.afterClosed().subscribe(projectPosition => {
      if (projectPosition) {
        projectPosition.project.id = editedProject.id;
        this.projectService.updateProject(projectPosition).subscribe(() => {
            this.loadProjects();
          }
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

  onSelectedProject(project: Project) {
    this.selectedProject = project;
    return this.drawer.open();
  }
}
