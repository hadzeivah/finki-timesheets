import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {ProjectService} from "../services/project.service";
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
  expandedDataSource = new MatTableDataSource();

  columnsToDisplay = ['chevron', 'projectName', 'total', 'estimatedBudget', 'difference'];
  expandedColumnsToDisplay = ['person', 'memberName', 'total'];
  expandedElement: ProjectTotalSalary;
  projectTotalSalaryList: ProjectTotalSalary[];

  constructor(private projectService: ProjectService) {
  }

  ngOnInit() {
    this.projectService.findReport().subscribe(projectTotalSalaryList => {
      this.projectTotalSalaryList = projectTotalSalaryList;
      this.dataSource.data = projectTotalSalaryList;
    });
  }

  onSelectedRow(element: ProjectTotalSalary) {
    this.expandedElement = element;
    this.expandedDataSource.data = element.memberTotalSalaryList;

  }
}
