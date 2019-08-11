import {Member} from "./Member";
import {University} from "./University";

export class Project {
  id: number;
  name: string;
  projectNumber: string;
  partnerOrganisation: string;
  startDate: any;
  endDate: any;
  university: University;
  members: Member[];
}

