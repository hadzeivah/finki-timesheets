import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatDialogModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import {MatSidenavModule} from "@angular/material/sidenav";

@NgModule({
  imports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule, MatInputModule, MatDialogModule,
    MatTableModule, MatPaginatorModule, MatSortModule, MatProgressSpinnerModule, MatDatepickerModule, MatNativeDateModule,
    MatListModule, MatIconModule, MatMenuModule, MatSelectModule, MatAutocompleteModule, MatTooltipModule, MatSidenavModule
    , MatSnackBarModule],
  exports: [CommonModule, MatToolbarModule, MatButtonModule, MatCardModule, MatInputModule, MatDialogModule,
    MatTableModule, MatPaginatorModule, MatSortModule, MatProgressSpinnerModule, MatDatepickerModule, MatNativeDateModule,
    MatListModule, MatIconModule, MatMenuModule, MatSelectModule, MatAutocompleteModule, MatTooltipModule,
    MatSidenavModule, MatSnackBarModule]
})
export class CustomMaterialModule {
}
