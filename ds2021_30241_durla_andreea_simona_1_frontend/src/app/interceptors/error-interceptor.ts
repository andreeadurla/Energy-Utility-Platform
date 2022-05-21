import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { AuthenticationService } from "../services/authentication/authentication.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor{
    
    constructor(private router: Router,
        private authService: AuthenticationService) {
    }
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        
        return next.handle(req).pipe(
            catchError(error => {

                if(error.status === 401) {
                    this.authService.logout();
                }
                
                return throwError(error);
            })
        )
    }
}
