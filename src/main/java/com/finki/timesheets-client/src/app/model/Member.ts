import {Project} from "./Project";
import {PositionType} from "./PositionType";

export class Member {

    id: number;
    firstName: String;
    lastName: String;
    fullName: String = this.firstName + " "  + this.lastName;
    embg: String;
    transactionAccount: String;
    positionType: PositionType;
    projects: Project[];

}

