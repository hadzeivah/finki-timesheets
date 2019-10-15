import {Project} from './Project';
import {Member} from './Member';
import {Item} from "./Item";

export class Timesheet {

  id: number;
  project: Project;
  member: Member;
  items: Item[];
  projectPosition: any
}

