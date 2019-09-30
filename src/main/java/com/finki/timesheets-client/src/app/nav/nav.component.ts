import {Component} from '@angular/core';
import {AuthService} from "../services/auth.service";

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

  myWorkRoutes: ROUTE[] = [
    {
      icon: 'assignment',
      route: 'documents',
      title: 'Documents',
    },
    {
      icon: 'assignment',
      route: 'reports',
      title: 'Reports',
    },
    {
      icon: 'dashboard',
      route: 'timesheets',
      title: 'Timesheets',
    }
  ];

  managementRoutes: ROUTE[] = [
    {
      icon: 'contacts',
      route: 'projects',
      title: 'Projects',
    }, {
      icon: 'people',
      route: 'list-user',
      title: 'Users',
    },
    {
      icon: 'people',
      route: 'members',
      title: 'Members',
    }
  ];

  constructor(private authService: AuthService) {
  }


  public isAuthenticated() {
    return this.authService.isAuthenticated();
  }

}

