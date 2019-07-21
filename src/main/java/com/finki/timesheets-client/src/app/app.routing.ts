import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AddUserComponent} from './users/add-user/add-user.component';
import {ListUserComponent} from './users/list-user/list-user.component';
import {EditUserComponent} from './users/edit-user/edit-user.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {TimesheetPageComponent} from "./pages/timesheet-page/timesheet-page.component";
import {DocumentPageComponent} from "./pages/document-page/document-page.component";
import {ProjectTableComponent} from "./projects/project-table/project-table.component";
import {MembersComponent} from "./members/members.component";

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
    path: 'documents',
    component: DocumentPageComponent
  },
  {
    path: 'projects',
    component: ProjectTableComponent
  },
  {
    path: 'members',
    component: MembersComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'timesheet/project/:projectId/member/:memberId',
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
