import {Component, inject, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatError, MatFormField, MatSuffix} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {RequestsService} from '../../services/requests.service';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-login-register-form',
  imports: [
    MatDialogContent,
    MatDialogTitle,
    MatButton,
    ReactiveFormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatError,
    MatDialogActions,
    MatIconButton,
    MatSuffix,
    MatIcon,
  ],
  templateUrl: './login-register-form.component.html',
  styleUrl: './login-register-form.component.scss'
})
export class LoginRegisterFormComponent {
    private requestsService = inject(RequestsService);
    private authService = inject(AuthService);

    data = inject(MAT_DIALOG_DATA);
    isLogin: boolean = this.data.isLogin;
    loginForm: FormGroup;
    registerForm: FormGroup;

    constructor(private formBuilder: FormBuilder) {
      this.loginForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      });

      this.registerForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(6), this.confirmPasswordValidator()]],
      });

    }


    toggleForm() {
      this.isLogin = !this.isLogin;
    }

    loginPasswordHidden = signal(true);
    registerPasswordHidden = signal(true);
    confirmPasswordHidden = signal(true);

    hideLoginPassword(event: MouseEvent) {
      this.loginPasswordHidden.set(!this.loginPasswordHidden());
      event.stopPropagation();
    }
    hidePassword(event: MouseEvent) {
      this.registerPasswordHidden.set(!this.registerPasswordHidden());
      event.stopPropagation();
    }
    hideConfirmPassword(event: MouseEvent) {
      this.confirmPasswordHidden.set(!this.confirmPasswordHidden());
      event.stopPropagation();
    }

    onLoginSubmit() {
      this.requestsService.getTest().subscribe(res => {
        console.log(res);
      })
    }

    onRegisterSubmit() {
      if(this.registerForm.valid) {
        let username: string = this.registerForm.get('username')?.value
        let password: string = this.registerForm.get('password')?.value
        console.log(password);
        console.log(username);

        this.authService.register(username, password).subscribe(res => {
          console.log(res);
        })
      }
    }

    confirmPasswordValidator(): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const password = control.parent?.get('password');
        const confirmPassword = control.parent?.get('confirmPassword');

        return password?.value === confirmPassword?.value ? null : { mismatch: true };

      }
    }


}
