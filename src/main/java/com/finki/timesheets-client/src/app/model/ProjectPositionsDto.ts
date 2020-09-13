import {Project} from "./Project";
import {WorkPackage} from "./WorkPackage";

export class ProjectPositionsDto {
  id: number;
  project: Project;
  positions: any;

  constructor(project: Project, positions: any, workPackage: WorkPackage) {
    this.project = project;
    this.project.workPackage = workPackage;
    this.positions = positions;
  }
}

