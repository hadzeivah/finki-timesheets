import {Output} from "./Output";
import {Task} from "./Task";

export class WorkPackage {
  id: number;
  name: string;
  tasks: Task [];
  outputs: Output [];
}

