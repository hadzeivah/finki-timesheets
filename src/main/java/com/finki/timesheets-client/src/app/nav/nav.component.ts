import {Component, EventEmitter, Output} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

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
      icon: 'folder_shared',
      route: 'members',
      title: 'Members',
    },
    {
      icon: 'folder',
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
    },
    {
      icon: 'how_to_reg',
      route: 'approval_request',
      title: 'Approval Request',
    }
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

