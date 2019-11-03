import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatTableDataSource} from "@angular/material/table";
import {ProjectTotalSalary} from "../model/ProjectTotalSalary";
import {ReportService} from "../services/report.service";
import {DownloadService} from "../services/download.service";
import {map} from "rxjs/operators";
import {ReportType} from "../model/ReportType";
import {ReportByIO} from "../model/ReportByIO";

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
  dataSource2 = new MatTableDataSource();
  expandedDataSource = new MatTableDataSource();
  isLoading: Boolean = true;
  columnsToDisplay = ['chevron', 'projectName', 'total', 'budget', 'difference'];
  columnsToDisplay2 = ['projectName', 'intellectualOutput', 'total'];
  expandedColumnsToDisplay = ['person', 'memberName', 'total', 'salary', 'position'];
  expandedElement: ProjectTotalSalary;
  projectTotalSalaryList: ProjectTotalSalary[];
  totalByIOList: ReportByIO[];
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  noData2 = this.dataSource2.connect().pipe(map(data => data.length === 0));
  reports: String [] = Object.values(ReportType);
  selectedReport: String = this.reports[0];

  get ReportType() {
    return ReportType;
  }


  constructor(private reportService: ReportService,
              private downloadService: DownloadService) {
  }

  ngOnInit() {
    this.findReportByProject();
    this.findReportByIO();
  }

  findReportByProject() {
    this.reportService.findReport().subscribe(projectTotalSalaryList => {
      this.projectTotalSalaryList = projectTotalSalaryList;
      this.dataSource.data = projectTotalSalaryList;
      this.isLoading = false;
    });

  }

  findReportByIO() {
    this.reportService.findReportTotalByIo().subscribe(
      totalByIO => {
        this.totalByIOList = totalByIO;
        this.dataSource2.data = totalByIO;
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

  onChange(report: any) {
    if (report.value === ReportType.TOTAL_PROJECTS) {
      this.selectedReport = report.value;
    } else if (report.value === ReportType.TOTAL_IO) {
      this.selectedReport = report.value;
    }
  }
}
