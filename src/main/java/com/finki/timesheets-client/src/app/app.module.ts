import { BrowserModule } from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { AddUserComponent } from './users/add-user/add-user.component';
import { EditUserComponent } from './users/edit-user/edit-user.component';
import { ListUserComponent } from './users/list-user/list-user.component';
import {ApiService} from './core/api.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {routing} from './app.routing';
import {TokenInterceptor} from './core/interceptor';
import { TimesheetComponent } from './timesheet/timesheet.component';
import {CustomMaterialModule} from './material/material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TimesheetService} from './services/timesheet.service';
import { ProjectListComponent } from './projects/project-list/project-list.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavComponent } from './nav/nav.component';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule } from '@angular/material';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { TemplateComponent } from './template/template.component';
import {LayoutModule} from '@angular/cdk/layout';
import { TimesheetPageComponent } from './pages/timesheet-page/timesheet-page.component';
import {AddProjectComponent} from "./projects/add-project/add-project.component";
import { AddMemberComponent } from './projects/add-member/add-member.component';
import {FilterPipe} from "./utils/filter-pipe";
import { ProjectTableComponent } from './projects/project-table/project-table.component';
import { DocumentPageComponent } from './pages/document-page/document-page.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AddUserComponent,
    EditUserComponent,
    ListUserComponent,
    TimesheetComponent,
    ProjectListComponent,
    DashboardComponent,
    NavComponent,
    NavigationBarComponent,
    TemplateComponent,
    TimesheetPageComponent,
    AddProjectComponent,
    AddMemberComponent,
    FilterPipe,
    ProjectTableComponent,
    DocumentPageComponent
  ],
  imports: [
    BrowserModule,
    routing,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CustomMaterialModule,
    BrowserAnimationsModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule
  ],
  providers: [ ErrorHandler, ApiService, TimesheetService, {provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi : true}],
  bootstrap: [AppComponent],
  entryComponents: [AddProjectComponent, AddMemberComponent]
})
export class AppModule { }