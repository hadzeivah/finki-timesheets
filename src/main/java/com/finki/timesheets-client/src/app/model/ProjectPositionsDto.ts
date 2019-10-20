import {Project} from "./Project";

export class ProjectPositionsDto {
  id: number;
  project: Project;
  positions: any;

  constructor(project: Project, positions: any) {
    this.project = project;
    this.positions = positions;
  }
}

