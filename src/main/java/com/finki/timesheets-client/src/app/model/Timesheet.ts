import {Project} from './Project';
import {Member} from './Member';

export class Timesheet {

  id: number;
  fromPeriod: string;
  toPeriod: string;
  project: Project;
  member: Member;
}

