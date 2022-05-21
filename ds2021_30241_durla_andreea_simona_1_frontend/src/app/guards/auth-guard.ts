import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { AuthenticationService } from "../services/authentication/authentication.service";
import { UserService } from "../services/user/user.service";

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    
    constructor(private router: Router,
        private authService: AuthenticationService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        const user = this.authService.user;

        if(Object.keys(user).length !== 0) {
            if(route.data.roles) {
                if(user.roles) {
                    let roles = user.roles;
                    for (var role of roles) {
                        if(route.data.roles.indexOf(role) !== -1) {
                            return true;
                        }
                    }
                }

                this.router.navigate(['/403error']);
                return false;
            }

            return true;
        }

        this.router.navigate(['/login']);
        return false;
    }
}
