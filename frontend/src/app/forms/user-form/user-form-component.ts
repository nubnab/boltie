import {Component, inject, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatError, MatFormField, MatSuffix} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {RequestsService} from '../../services/requests.service';
import {AuthService} from '../../services/auth.service';
import PasswordValidator from '../../validators/password-validator.validator';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-form',
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
  templateUrl: './user-form-component.html',
  styleUrl: './user-form-component.scss'
})

export class UserFormComponent {

    private requestsService = inject(RequestsService);
    private authService = inject(AuthService);
    private data = inject(MAT_DIALOG_DATA);
    private router = inject(Router);


    isLogin: boolean = this.data.isLogin;
    loginForm: FormGroup;
    registerForm: FormGroup;

    constructor(private formBuilder: FormBuilder) {
      this.loginForm = this.formBuilder.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
      });

      this.registerForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(255)]],
        password: ['', [Validators.required, PasswordValidator.minLengthValidator(8),
                        Validators.maxLength(255), PasswordValidator.passwordRequirementValidator()]],
        confirmPassword: ['', [Validators.required, PasswordValidator.confirmPasswordValidator()]],
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

    onLoginSubmit(): void {
      this.authService.login(this.loginForm.get('username')?.value,
                             this.loginForm.get('password')?.value).subscribe({
        next: (res) => {
          this.authService.setAuthToken(res.token);
          void this.router.navigateByUrl('/');
          console.log("Successfully logged in", res);
        },
        error: (err) => {
          this.authService.setAuthToken(null);
          console.log("Login failed", err);
        }
      })
    }

    onRegisterSubmit(): void {
      if(this.registerForm.valid) {
        let username: string = this.registerForm.get('username')?.value
        let password: string = this.registerForm.get('password')?.value

        this.authService.register(username, password).subscribe({
          next: (res) => {
            this.authService.setAuthToken(res.token);
            console.log("Login success", res);
          },
            error: (err) => {
            this.authService.setAuthToken(null);
            console.log("Login fail", err);
          }
        })
      }
    }

    hasError(errorName: string): boolean {
      const password = this.registerForm.get('password');
      return !!password?.errors?.[errorName];
    }
}
