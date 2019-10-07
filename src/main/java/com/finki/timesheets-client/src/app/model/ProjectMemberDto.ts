import {Project} from "./Project";
import {Member} from "./Member";
import {PositionType} from "./PositionType";

export class ProjectMemberDto {

  project: Project;
  member: Member;
  positionType: PositionType;

  constructor(project: Project, member: Member, positionType: PositionType) {
    this.project = project;
    this.member = member;
    this.positionType = positionType;
  }
}
