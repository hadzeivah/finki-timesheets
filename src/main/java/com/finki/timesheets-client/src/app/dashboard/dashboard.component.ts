import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material";
import {AddProjectComponent} from "../projects/add-project/add-project.component";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  name: string;

  constructor() { }

  ngOnInit() {
  }


}
