import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {User} from "../../model/User";

@Component({
  selector: 'app-list-user',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent implements OnInit {

  dataSource: User[];
  displayedColumns: string[] = ['firstName', 'lastName', 'username' , 'actions'];

  constructor(private router: Router, private apiService: AuthService) { }

  ngOnInit() {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.apiService.getUsers()
      .subscribe( data => {
          this.dataSource = data.result;
      });
  }

  deleteUser(user: User): void {
    this.apiService.deleteUser(user.id)
      .subscribe( data => {
        this.dataSource = this.dataSource.filter(u => u !== user);
      });
  }

  editUser(user: User): void {
    window.localStorage.removeItem('editUserId');
    window.localStorage.setItem('editUserId', user.id.toString());
    this.router.navigate(['edit-user']);
  }

  addUser(): void {
    this.router.navigate(['add-user']);
  }
}
