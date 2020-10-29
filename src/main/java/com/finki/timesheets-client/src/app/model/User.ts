export class User {

  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  authorities: Authority[];
}

class Authority {
  authority: string;
}
