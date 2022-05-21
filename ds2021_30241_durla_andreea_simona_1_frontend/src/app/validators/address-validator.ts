import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class AddressValidator {

    static valid(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const value = control.value;
            const regex = /^(?=.*[!@#\$%\^&\*()])/;

            if(control.errors && !control.errors.valid) {
                return null;
            }

            let invalid = false;

            if(value) {
                invalid = regex.test(value);  
                control.markAsTouched();
            }

            return invalid ? { valid: true }: null;
        };
    }
}
