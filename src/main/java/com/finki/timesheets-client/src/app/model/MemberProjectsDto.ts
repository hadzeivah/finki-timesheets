import {Member} from "./Member";
import {ProjectPositionDto} from "./ProjectPositionDto";

export class MemberProjectsDto {
  member: Member;
  projectPosition: ProjectPositionDto[]
}
