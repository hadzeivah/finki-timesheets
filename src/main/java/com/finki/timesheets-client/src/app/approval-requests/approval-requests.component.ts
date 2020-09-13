import {Component, OnInit, ViewChild} from '@angular/core';
import {Project} from "../model/Project";
import {Member} from "../model/Member";
import {MatTableDataSource} from "@angular/material/table";
import {map} from "rxjs/operators";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ProjectService} from "../services/project.service";
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-approval-requests',
  templateUrl: './approval-requests.component.html',
  styleUrls: ['./approval-requests.component.css']
})
export class ApprovalRequestsComponent implements OnInit {

  projects: Project[];
  selectedProject: Project;
  members: Member[];
  dataSource = new MatTableDataSource<Project>();
  displayedColumns: string[] = ['projectName', 'projectNumber', 'university', 'projectManager', 'startDate', 'endDate', 'actions'];
  isLoading: Boolean = true;
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private projectService: ProjectService,
              private notificationService: NotificationService) {
  }


  ngOnInit() {
    this.loadApprovedProjects();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadApprovedProjects() {
    this.projectService.findUnapprovedProjects()
      .subscribe(data => {
          this.dataSource.data = data.result;
          this.projects = data.result;
          this.isLoading = false;
        }, err => console.log('HTTP Error', err),
      );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  approveProject(project: Project) {
    this.projectService.approveProject(project).subscribe(result => {
      this.dataSource.data = this.dataSource.data.filter(p => p.id !== project.id);
      this.notificationService.openSnackBar(result.message)
    });
  }
}
