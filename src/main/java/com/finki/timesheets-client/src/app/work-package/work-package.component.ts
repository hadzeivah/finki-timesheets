import {Component, OnInit} from '@angular/core';
import {WorkPackagesService} from "../services/work-packages.service";
import {WorkPackage} from "../model/WorkPackage";
import {FormControl, Validators} from "@angular/forms";
import {Output} from "../model/Output";
import {Task} from "../model/Task";

@Component({
  selector: 'app-work-package',
  templateUrl: './work-package.component.html',
  styleUrls: ['./work-package.component.css']
})
export class WorkPackageComponent implements OnInit {

  panelOpenState = false;
  workPackages: WorkPackage[];
  tasks: Task [] = [];
  outputs: Output [] = [];
  taskDescription = new FormControl('', Validators.required);
  intellectualOutput = new FormControl('', Validators.required);
  workPackageName = new FormControl('', Validators.required);

  constructor(public workPackagesService: WorkPackagesService) {
  }

  ngOnInit() {
    this.getWorkPackages();
  }

  getWorkPackages() {
    this.workPackagesService.findWorkPackages()
      .subscribe(data => {
          this.workPackages = data;
        }, err => console.log('HTTP Error', err),
      );

  }

  addTask(workPackage: WorkPackage) {
    workPackage.tasks.push(new Task(this.taskDescription.value));
    this.workPackagesService.saveTasks(workPackage).subscribe();
  }

  addOutput(workPackage: WorkPackage) {
    workPackage.outputs.push(new Output(this.taskDescription.value));
    this.workPackagesService.saveOutputs(workPackage).subscribe();
  }

  addWorkPackage() {
    let workPackage = new WorkPackage(this.workPackageName.value);
    this.workPackagesService.saveWorkPackage(workPackage).subscribe(
      workPackage => {
        this.workPackages.push(workPackage);
      });
  }

  deleteTask(taskId: number) {

  }

  deleteOutput(id: number) {

  }
}
