import {Component, inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogContent, MatDialogTitle} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-login-register-form',
  imports: [
    MatDialogContent,
    MatDialogTitle,
    MatButton
  ],
  templateUrl: './login-register-form.component.html',
  styleUrl: './login-register-form.component.scss'
})
export class LoginRegisterFormComponent {
    data = inject(MAT_DIALOG_DATA);
    isLogin = this.data.isLogin;
    loginForm: FormGroup;
    registerForm: FormGroup;

    constructor(private formBuilder: FormBuilder) {
      this.loginForm = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      });

      this.registerForm = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      });
    }

    toggleForm() {
      this.isLogin = !this.isLogin;
    }


}
