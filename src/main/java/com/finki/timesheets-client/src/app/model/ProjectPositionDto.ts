import {Project} from "./Project";

export class ProjectPositionDto {
  project: Project;
  positions: any;

  constructor(project: Project, positions: any) {
    this.project = project;
    this.positions = positions;
  }
}

