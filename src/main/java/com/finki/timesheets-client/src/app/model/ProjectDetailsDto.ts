import {Project} from "./Project";
import {WorkPackage} from "./WorkPackage";
import {PositionSalaryDto} from "./PositionSalaryDto";

export class ProjectDetailsDto {
  id: number;
  project: Project;
  positions: PositionSalaryDto[];
  positionsToDelete: PositionSalaryDto[];
  changed = false;


  constructor(project: Project, positions: PositionSalaryDto[], positionsToDelete: PositionSalaryDto[], workPackage: WorkPackage) {
    this.project = project;
    this.project.workPackage = workPackage;
    this.positions = positions;
    this.positionsToDelete = positionsToDelete;
  }
}

