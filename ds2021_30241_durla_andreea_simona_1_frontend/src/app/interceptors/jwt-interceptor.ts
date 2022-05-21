import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthenticationService } from "../services/authentication/authentication.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{
    
    constructor(private authService: AuthenticationService) {
    }
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
        const user = this.authService.user;
        const isLoggedIn = user && user.token;

        if(isLoggedIn) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${user.token}`
                }
            });
        }

        return next.handle(req);
    }
}
