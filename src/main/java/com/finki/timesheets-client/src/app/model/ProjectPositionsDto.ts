import {Project} from "./Project";

export class ProjectPositionsDto {
  project: Project;
  positions: any;

  constructor(project: Project, positions: any) {
    this.project = project;
    this.positions = positions;
  }
}

