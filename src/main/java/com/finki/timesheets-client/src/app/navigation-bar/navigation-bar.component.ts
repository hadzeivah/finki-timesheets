import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthService} from "../core/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.scss']
})
export class NavigationBarComponent implements OnInit {
  @Output() toggleSidenav = new EventEmitter<void>();

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  logout() {
    AuthService.logout();
    this.router.navigate(['login']);
  }

  public isUserLoggedIn(){
    return this.authService.isLoggedIn();
  }
}
