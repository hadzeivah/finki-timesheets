import {Injectable} from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {


  constructor(public snackBar: MatSnackBar) {
  }

  openSnackBar(message: string) {
    this.openSnackBarWithAction(message, 'Close');
  }

  openSnackBarWithAction(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 150000,
    });
  }
}
