import {Task} from './Task';
import {Output} from "./Output";

export class Item {
  id: number;
  timesheetId: number;
  date: string;
  hours: number;
  taskDescription: Task;
  intellectualOutput: Output;
  editing: boolean;
  exceededWorkingHours: boolean = false;
}

