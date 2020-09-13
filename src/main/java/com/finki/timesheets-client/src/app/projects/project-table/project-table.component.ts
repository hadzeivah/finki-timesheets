import {Component, OnInit, ViewChild} from '@angular/core';
import {Project} from "../../model/Project";
import {ProjectService} from "../../services/project.service";
import {AddProjectComponent} from "../add-project/add-project.component";
import {MatDialog, MatTableDataSource} from "@angular/material";
import {Member} from "../../model/Member";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AssignMemberComponent} from "../assign-member/assign-member.component";
import {ProjectMemberDto} from "../../model/ProjectMemberDto";
import {MemberService} from "../../services/member.service";
import {map} from "rxjs/operators";
import {ConfirmDialogComponent} from "../../confirm-dialog/confirm-dialog.component";
import {NotificationService} from "../../services/notification.service";


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
  displayedColumns: string[] = ['projectName', 'projectNumber', 'university', 'projectManager', 'startDate', 'endDate', 'approved', 'actions'];
  isLoading: Boolean = true;
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private projectService: ProjectService,
              private memberService: MemberService,
              public dialog: MatDialog,
              public notificationService: NotificationService) {
  }

  ngOnInit() {
    this.loadProjects();
    this.loadMembers();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadProjects() {
    this.projectService.findProjects()
      .subscribe(data => {
          this.dataSource.data = data.result;
        this.projects = data.result;
        this.isLoading = false;
        }, err => console.log('HTTP Error', err),
      );
  }

  loadMembers() {
    this.memberService.findMembers()
      .subscribe(members =>
        this.members = members.result);
  }

  addProjectDialog() {

    const dialogRef = this.dialog.open(AddProjectComponent, {
      autoFocus: true,
      width: '600'
    });

    dialogRef.afterClosed().subscribe(projectPosition => {
      if (projectPosition) {

        console.log(projectPosition);
        this.projectService.addProject(projectPosition).subscribe(() => {
          this.notificationService.openSnackBar('Project successfully added');
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
        projectPosition.project.id = editedProject != null ? editedProject.id : null;
        projectPosition.project.projectManager = editedProject.projectManager;

        this.projectService.updateProject(projectPosition).subscribe(() => {

          // const foundIndex = this.projects.findIndex(x => x.id === editedProject.id);
          // this.projects[foundIndex] = projectPosition;
          // this.dataSource.data = this.projects;
          this.loadProjects();

          }
        )
      }
    });
  }


  deleteProject(project: Project): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: false
    });

    dialogRef.componentInstance.confirmMessage = "Are you sure you want to delete?"

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // do confirmation actions
        this.projectService.deleteProject(project.id)
          .subscribe(data => {
            this.dataSource.data = this.dataSource.data.filter(p => p !== project);
          });
      }
    });

  }

  onSelectedProject(project: Project) {
    this.selectedProject = project;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addMemberToProjectDialog(project: any) {

    const dialogRef = this.dialog.open(AssignMemberComponent, {
      autoFocus: true,
      width: '600',
      data: {
        project: project,
        members: this.members
      }
    });

    dialogRef.afterClosed().subscribe(memberPosition => {
      if (memberPosition) {
        this.members = this.members.filter(member => member.id != memberPosition.id);
        this.projectService.assignMemberToProject(new ProjectMemberDto(project, memberPosition.member, memberPosition.positionType))
          .subscribe(result => {
            this.notificationService.openSnackBar('Member successfully assigned');
          })
      }
    });

  }
}
