import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AddUserComponent} from './users/add-user/add-user.component';
import {UserTableComponent} from './users/user-table/user-table.component';
import {EditUserComponent} from './users/edit-user/edit-user.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {TimesheetPageComponent} from "./pages/timesheet-page/timesheet-page.component";
import {DocumentPageComponent} from "./pages/document-page/document-page.component";
import {ProjectTableComponent} from "./projects/project-table/project-table.component";
import {MembersComponent} from "./members/members.component";
import {AuthGuardService as AuthGuard } from "./services/guards/auth-guard.service";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'add-user',
    component: AddUserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'list-user',
    component: UserTableComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'edit-user',
    component: EditUserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'documents',
    component: DocumentPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'projects',
    component: ProjectTableComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'members',
    component: MembersComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
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
