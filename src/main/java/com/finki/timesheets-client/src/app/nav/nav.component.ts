import { Component } from '@angular/core';

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
    }, {
      icon: 'dashboard',
      route: 'dashboard',
      title: 'Dashboards',
    }
  ];

  customerRoutes: ROUTE[] = [
    {
      icon: 'contacts',
      route: 'projects',
      title: 'Projects',
    }, {
      icon: 'people',
      route: 'list-user',
      title: 'Users',
    }
  ];

  constructor(/*private authService: AuthService*/) {}


  public isAuthenticated() {
    return true;
  }

}

