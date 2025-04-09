import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';
import {UsernameDto} from '../pages/settings/settings.component';

function usernameTakenValidator(existingUsernames: UsernameDto[]): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null  => {
    const username = control.value?.toLowerCase();

    const validationErrors = {
      usernameTaken: true,
    };

    const isTaken = existingUsernames.some(user =>
      user.username.toLowerCase() === username
    );

    console.log(isTaken);

    return !isTaken ? null : validationErrors;
  };
}

 const UsernameTakenValidator = {
   usernameTakenValidator
 }

 export default UsernameTakenValidator;
