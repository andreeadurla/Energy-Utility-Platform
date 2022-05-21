import { AbstractControl, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

export class PasswordValidator {

    static valid(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,50})/;

            if(control.errors && !control.errors.valid) {
                return null;
            }

            let valid = true;

            if(value) {
                valid = regex.test(value);  
                control.markAsTouched();
            }

            return !valid ? { valid: true }: null;
        }
    }

    static match(controlName: string, matchingControlName: string) {
        return (formGroup: FormGroup) => {
            const control = formGroup.controls[controlName];
            const matchingControl = formGroup.controls[matchingControlName];
    
            if(matchingControl.errors && !matchingControl.errors.match) {
                return ;
            }
    
            if(control.value !== matchingControl.value) {
                matchingControl.setErrors({ match: true });
            } else {
                matchingControl.setErrors(null);
            }
    
            matchingControl.markAsTouched();
        }
    }
}
