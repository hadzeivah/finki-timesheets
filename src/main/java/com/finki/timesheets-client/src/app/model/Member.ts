export class Member {

    id: number;
    firstName: String;
    lastName: String;
    fullName: String = this.firstName + " "  + this.lastName;
    embg: String;
    transactionAccount: String;
}

