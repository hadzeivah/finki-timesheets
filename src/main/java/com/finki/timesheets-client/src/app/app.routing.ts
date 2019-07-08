import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AddUserComponent} from './users/add-user/add-user.component';
import {ListUserComponent} from './users/list-user/list-user.component';
import {EditUserComponent} from './users/edit-user/edit-user.component';
import {TimesheetComponent} from './timesheet/timesheet.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {TimesheetPageComponent} from "./pages/timesheet-page/timesheet-page.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'add-user',
    component: AddUserComponent
  },
  {
    path: 'list-user',
    component: ListUserComponent
  },
  {
    path: 'edit-user',
    component: EditUserComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'timesheet/:projectId/:memberId',
        component: TimesheetPageComponent
      }
    ]
  },
  {
    path: '',
    component: LoginComponent
  }
];

export const routing = RouterModule.forRoot(routes);
