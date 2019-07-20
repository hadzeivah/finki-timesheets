import {Member} from "./Member";

export class Project {
  id: number;
  name: string;
  projectNumber: string;
  partnerOrganisation: string;
  startDate: any;
  endDate: any;
  members: Member[];
}

