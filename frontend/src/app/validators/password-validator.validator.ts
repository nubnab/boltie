import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

function passwordRequirementValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value;

    const hasLowerCase = /[a-z]/.test(password);
    const hasUpperCase = /[A-Z]/.test(password);
    const hasNumeric = /[0-9]/.test(password);
    const hasSymbol = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password);

    const isPasswordValid =
      hasLowerCase && hasUpperCase && hasNumeric && hasSymbol;

    const validationErrors = {
      hasLowerCase: !hasLowerCase,
      hasUpperCase: !hasUpperCase,
      hasNumeric: !hasNumeric,
      hasSymbol: !hasSymbol,
    }

    return isPasswordValid ? null : validationErrors;
  }
}

function confirmPasswordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.parent?.get('password');
    const confirmPassword = control.parent?.get('confirmPassword');

    password?.valueChanges.subscribe(() => {
      confirmPassword?.updateValueAndValidity();
    });

    return password?.value === confirmPassword?.value ? null : { mismatch: true };

  }
}

function minLengthValidator(minLength: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.value || '';
    if(!password || password.length < minLength) {
      return { minLength: true };
    }
    return null;
  }
}

const PasswordValidator = {
  passwordRequirementValidator,
  confirmPasswordValidator,
  minLengthValidator,
}

export default PasswordValidator;
