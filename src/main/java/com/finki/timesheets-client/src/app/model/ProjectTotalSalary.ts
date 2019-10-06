import {MemberTotalSalary} from "./MemberTotalSalary";


export class ProjectTotalSalary {
  id: number;
  name: string;
  estimatedBudget: string;
  totalSalary: number;
  memberTotalSalaryList: MemberTotalSalary[];
}

