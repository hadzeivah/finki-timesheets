import {Component, EventEmitter, Output} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

/*
import { AuthService } from 'auth';
*/

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
  @Output() toggleSidenav = new EventEmitter<void>();

  routes: ROUTE[] = [
    {
      icon: 'file_copy',
      route: 'documents',
      title: 'Documents',
    },
    {
      icon: 'insert_chart',
      route: 'reports',
      title: 'Reports',
    },
    {
      icon: 'table_chart',
      route: 'timesheets',
      title: 'Timesheets',
    },
    {
      icon: 'folder',
      route: 'projects',
      title: 'Projects',
    },
    {
      icon: 'folder_shared',
      route: 'members',
      title: 'Members',
    },
    {
      icon: 'people',
      route: 'list-user',
      title: 'Users',
    },
  ];

  constructor(private authService: AuthService, private router: Router) {
  }


  public isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  public getLoggedUser() {
    return this.authService.getLoggedUser();
  }

  logout() {
    AuthService.logout();
    this.router.navigate(['login']);
  }


}

