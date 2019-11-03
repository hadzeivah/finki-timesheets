import {Injectable} from '@angular/core';
import {AuthService} from "../auth.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    const token = JSON.parse(localStorage.getItem('currentUser')).token;

    // decoding the token to get its payload
    const tokenPayload = decode(token);

    if (!this.auth.isAuthenticated() || tokenPayload.scopes !== expectedRole) {
      this.router.navigate(['login']);
      this.auth.logout();
      return false;
    }
    return true;
  }
}
