import {University} from "./University";
import {Timesheet} from "./Timesheet";
import {User} from "./User";
import {WorkPackage} from "./WorkPackage";

export class Project {
  id: number;
  name: string;
  projectNumber: string;
  estimatedBudget: number;
  startDate: string;
  endDate: string;
  university: University;
  projectManager: User;
  timesheets: Timesheet[];
  approved: boolean;
  workPackage: WorkPackage;
  deleted: boolean;

  public constructor(init?: Partial<Project>) {
    Object.assign(this, init);
  }
}

