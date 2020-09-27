import {WorkPackage} from "./WorkPackage";

export class Task {
  id: number;
  description: string;
  workPackage: WorkPackage;

  constructor(description: string) {
    this.description = description;
  }
}
