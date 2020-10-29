import {Injectable} from '@angular/core';
import {AuthService} from "../auth.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));

    if (!this.auth.isAuthenticated() || currentUser.authorities.find(authority => authority.authority !== expectedRole)) {
      this.router.navigate(['login']);
      this.auth.logout();
      return false;
    }
    return true;
  }
}
