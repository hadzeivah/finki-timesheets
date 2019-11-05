import {Component, EventEmitter, Output} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {Role} from "../model/Role";
import {User} from "../model/User";

interface ROUTE {
  icon?: string;
  route?: string;
  title?: string;
}

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {
  isExpanded = true;
  currentUser: User;
  @Output() toggleSidenav = new EventEmitter<void>();

  routes: ROUTE[] = [
    {
      icon: 'folder_shared',
      route: 'members',
      title: 'Members',
    },
    {
      icon: 'folder',
      route: 'work_package',
      title: 'Work Package',
    },
    {
      icon: 'create_new_folder',
      route: 'projects',
      title: 'Projects',
    },

    {
      icon: 'table_chart',
      route: 'timesheets',
      title: 'Timesheets',
    },
    {
      icon: 'file_copy',
      route: 'documents',
      title: 'Documents',
    },
    {
      icon: 'insert_chart',
      route: 'reports',
      title: 'Reports',
    }
  ];
  admin_routes: ROUTE[] = [
    {
      icon: 'how_to_reg',
      route: 'approval_request',
      title: 'Requests'
    }
  ];

  constructor(private authService: AuthService, private router: Router) {
    this.authService.currentUser.subscribe(user => this.currentUser = user);

  }


  public isAuthenticated() {
    return this.authService.isAuthenticated();
  }


  get isAdmin() {
    return this.currentUser && this.currentUser.role === Role.Admin;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }


}

