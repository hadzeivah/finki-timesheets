import {Component, OnInit} from '@angular/core';
import {WorkPackagesService} from "../services/work-packages.service";
import {WorkPackage} from "../model/WorkPackage";
import {FormControl, Validators} from "@angular/forms";
import {Output} from "../model/Output";
import {Task} from "../model/Task";
import {TaskService} from "../services/task.service";
import {IntellectualOutputService} from "../services/intellectualOutput.service";

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

  constructor(private workPackagesService: WorkPackagesService,
              private taskService: TaskService,
              private outputService: IntellectualOutputService) {
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
    let task = new Task(this.taskDescription.value);
    this.workPackagesService.saveTask(workPackage.id, task).subscribe(
      task => workPackage.tasks.push(task)
    );
  }

  addOutput(workPackage: WorkPackage) {
    let output = new Output(this.intellectualOutput.value);
    this.workPackagesService.saveOutput(workPackage.id, output).subscribe(
      output => workPackage.outputs.push(output)
    );
  }

  addWorkPackage() {
    let workPackage = new WorkPackage(this.workPackageName.value);
    this.workPackagesService.saveWorkPackage(workPackage).subscribe(
      workPackage => {
        this.workPackages.push(workPackage);
      });
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe(() =>
      this.getWorkPackages()
    );
  }

  deleteOutput(id: number) {
    this.outputService.deleteOutput(id).subscribe(() =>
      this.getWorkPackages()
    )
  };
}
