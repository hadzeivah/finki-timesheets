import {Task} from './Task';
import {Output} from "./Output";

export class Item {
  id: number;
  timesheetId: number;
  startDate: string;
  endDate: string;
  hours: number;
  taskDescription: Task;
  intellectualOutput: Output;
  editing: boolean;
}

