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
import {ProjectListComponent} from './projects/project-list/project-list.component';
import {NavComponent} from './nav/nav.component';
import {
  MAT_DATE_LOCALE,
  MatButtonModule,
  MatIconModule,
  MatListModule,
  MatSidenavModule,
  MatToolbarModule
} from '@angular/material';
import {TemplateComponent} from './template/template.component';
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
import {NavigationBarComponent} from "./navigation-bar/navigation-bar.component";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {MatStepperModule} from "@angular/material/stepper";
import {ReportsComponent} from './reports/reports.component';
import {ReportsPageComponent} from './pages/reports-page/reports-page.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AddUserComponent,
    EditUserComponent,
    UserTableComponent,
    TimesheetComponent,
    ProjectListComponent,
    NavComponent,
    NavigationBarComponent,
    TemplateComponent,
    TimesheetPageComponent,
    AddProjectComponent,
    AddMemberComponent,
    FilterPipe,
    ProjectTableComponent,
    DocumentPageComponent,
    MembersComponent,
    ReportsComponent,
    ReportsPageComponent
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
    MatListModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatStepperModule
  ],
  providers: [ErrorHandler, AuthService, TimesheetService, TranslateService, JwtHelperService,
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},],
  bootstrap: [AppComponent],
  entryComponents: [AddProjectComponent, AddMemberComponent]
})
export class AppModule {
}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
