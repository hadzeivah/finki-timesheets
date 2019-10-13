import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {ProjectTotalSalary} from "../model/ProjectTotalSalary";
import {ReportService} from "../services/report.service";
import {DownloadService} from "../services/download.service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed, void', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      transition('expanded <=> void', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ],
})


export class ReportsComponent implements OnInit {
  dataSource = new MatTableDataSource();
  expandedDataSource = new MatTableDataSource();
  isLoading: Boolean = true;
  columnsToDisplay = ['chevron', 'projectName', 'total', 'budget', 'difference'];
  expandedColumnsToDisplay = ['person', 'memberName', 'total', 'salary', 'position'];
  expandedElement: ProjectTotalSalary;
  projectTotalSalaryList: ProjectTotalSalary[];

  constructor(private reportService: ReportService,
              private downloadService: DownloadService) {
  }

  ngOnInit() {
    this.reportService.findReport().subscribe(projectTotalSalaryList => {
      this.projectTotalSalaryList = projectTotalSalaryList;
      this.dataSource.data = projectTotalSalaryList;
      this.isLoading = false;
    });
  }

  onSelectedRow(element: ProjectTotalSalary) {
    this.expandedElement = element;
    this.expandedDataSource.data = element.memberTotalSalaryList;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  exportToExcel() {
    this.reportService.exportReportToExcel().subscribe(
      response => {
        const filename = this.downloadService.getFileNameFromResponseContentDisposition(response);
        this.downloadService.saveFile(response.body, filename)
      }
    );
  }
}
