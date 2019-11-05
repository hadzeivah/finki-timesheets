import {Component, OnInit} from '@angular/core';
import {WorkPackagesService} from "../services/work-packages.service";
import {WorkPackage} from "../model/WorkPackage";

@Component({
  selector: 'app-work-package',
  templateUrl: './work-package.component.html',
  styleUrls: ['./work-package.component.css']
})
export class WorkPackageComponent implements OnInit {

  panelOpenState = false;
  workPackages: WorkPackage[];

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
}
