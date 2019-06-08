import {FormGroup} from '@angular/forms';

export abstract class Item {
  id: number;
  timesheetId: number;
  startDate: string;
  endDate: string;
  hours: number;
  taskDescription: string;
  intellectualOutput: string;
  editing: boolean;
}

