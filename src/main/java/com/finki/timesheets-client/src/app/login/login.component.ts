import {Component} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private formBuilder: FormBuilder, private router: Router, private authService: AuthService,
              private notificationService: NotificationService) {
    this.authService.login().subscribe(user => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.router.navigate(["projects"]);
        if (user == null) {
          notificationService.openSnackBar("Not allowed access");
        }
      }
      , error => notificationService.openSnackBar(error.message));
  }
}


