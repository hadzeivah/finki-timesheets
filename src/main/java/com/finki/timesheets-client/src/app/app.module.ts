import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {AddUserComponent} from './users/add-user/add-user.component';
import {EditUserComponent} from './users/edit-user/edit-user.component';
import {UserTableComponent} from './users/user-table/user-table.component';
import {AuthService} from './services/auth.service';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {routing} from './app.routing';
import {TokenInterceptor} from './services/interceptor';
import {TimesheetComponent} from './timesheet/timesheet.component';
import {CustomMaterialModule} from './material/material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TimesheetService} from './services/timesheet.service';
import {NavComponent} from './nav/nav.component';
import {MAT_DATE_LOCALE} from '@angular/material';
import {DocumentComponent} from './documents/document.component';
import {LayoutModule} from '@angular/cdk/layout';
import {TimesheetPageComponent} from './pages/timesheet-page/timesheet-page.component';
import {AddProjectComponent} from "./projects/add-project/add-project.component";
import {AddMemberComponent} from './members/add-member/add-member.component';
import {FilterPipe} from "./utils/filter-pipe";
import {ProjectTableComponent} from './projects/project-table/project-table.component';
import {DocumentPageComponent} from './pages/document-page/document-page.component';
import {MembersComponent} from './members/members.component';
import {TranslateLoader, TranslateModule, TranslateService} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {MatStepperModule} from "@angular/material/stepper";
import {ReportsComponent} from './reports/reports.component';
import {ReportsPageComponent} from './pages/reports-page/reports-page.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatSortModule} from "@angular/material/sort";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatTooltipModule} from "@angular/material/tooltip";
import {AssignMemberComponent} from './projects/assign-member/assign-member.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AddUserComponent,
    EditUserComponent,
    UserTableComponent,
    TimesheetComponent,
    NavComponent,
    DocumentComponent,
    TimesheetPageComponent,
    AddProjectComponent,
    AddMemberComponent,
    FilterPipe,
    ProjectTableComponent,
    DocumentPageComponent,
    MembersComponent,
    ReportsComponent,
    ReportsPageComponent,
    AssignMemberComponent
  ],
  imports: [
    BrowserModule,
    routing,
    FormsModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    HttpClientModule,
    CustomMaterialModule,
    BrowserAnimationsModule,
    LayoutModule,

    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatStepperModule,
    MatProgressBarModule,
    MatSortModule,
    MatTooltipModule
  ],
  providers: [ErrorHandler, AuthService, TimesheetService, TranslateService, JwtHelperService,
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},],
  bootstrap: [AppComponent],
  entryComponents: [AddProjectComponent, AddMemberComponent, AssignMemberComponent]
})
export class AppModule {
}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
