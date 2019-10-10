export class Member {

  id: number;
  firstName: string;
  lastName: string;
  fullName: string = this.firstName + " " + this.lastName;
  embg: string;
  transactionAccount: string;
}
