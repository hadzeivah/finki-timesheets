import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {ProjectService} from "../services/project.service";
import {Project} from "../model/Project";
import {ProjectTotalSalary} from "../model/ProjectTotalSalary";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})


export class ReportsComponent implements OnInit {
  dataSource = new MatTableDataSource();
  columnsToDisplay = ['projectName', 'total', 'expected', 'difference'];
  expandedElement: Project;
  projectTotalSalaryList: ProjectTotalSalary[];

  constructor(private projectService: ProjectService) {
  }

  ngOnInit() {
    this.projectService.findReport().subscribe(report => {
      this.projectTotalSalaryList = report;
      this.dataSource.data = report;
    });
  }
}
