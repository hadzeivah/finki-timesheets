import {WorkPackage} from "./WorkPackage";

export class Output {
  id: number;
  description: string;
  workPackage: WorkPackage;

  constructor(description: string) {
    this.description = description;
  }
}
