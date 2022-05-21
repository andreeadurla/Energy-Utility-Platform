import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class UsernameValidator {

    static valid(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            const regex = /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,28}[a-zA-Z0-9]$/;

            if(control.errors && !control.errors.valid) {
                return null;
            }

            let valid = true;

            if(value) {
                valid = regex.test(value);  
                control.markAsTouched();
            }

            return !valid ? { valid: true }: null;
        };
    }
}
