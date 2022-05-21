import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class NameValidator {

    static valid(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            const regex = /^[a-zA-Z](['\- ](?!['\- ])|[a-zA-Z]){1,48}[a-zA-Z]$/;

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
