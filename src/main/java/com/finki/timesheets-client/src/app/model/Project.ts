import {University} from "./University";
import {Timesheet} from "./Timesheet";

export class Project {
  id: number;
  name: string;
  projectNumber: string;
  estimatedBudget: number;
  startDate: string;
  endDate: string;
  university: University;
  timesheets: Timesheet[];
}

