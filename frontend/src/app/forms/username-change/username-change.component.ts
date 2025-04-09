import {Component, Inject, inject, signal} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatButton, MatIconButton} from '@angular/material/button';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatError, MatFormField, MatLabel, MatSuffix} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatInput} from '@angular/material/input';
import {RequestsService} from '../../services/requests.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthService} from '../../services/auth.service';
import {UsernameDto} from '../../pages/settings/settings.component';
import UsernameTakenValidator from '../../validators/username-taken.validator';

@Component({
  selector: 'app-username-change',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    FormsModule,
    MatFormField,
    MatIcon,
    MatIconButton,
    MatInput,
    MatLabel,
    MatSuffix,
    ReactiveFormsModule,
    MatError,
  ],
  templateUrl: './username-change.component.html',
  styleUrl: './username-change.component.scss'
})

export class UsernameChangeComponent {

  private requestsService = inject(RequestsService);
  private authService = inject(AuthService);
  private jwtHelper = new JwtHelperService();

  passwordHidden = signal(true);

  usernameChangeForm: FormGroup;

  constructor(private formBuilder: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: UsernameDto[]) {
    this.usernameChangeForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(4),
        Validators.maxLength(255), UsernameTakenValidator.usernameTakenValidator(this.data)]],
      password: ['', [Validators.required]],
    });

  }

  togglePasswordVisibility(event: MouseEvent) {
    this.passwordHidden.set(!this.passwordHidden());
    event.stopPropagation();
  }

  submitNameChangeRequest() {
    const username: string = (this.usernameChangeForm.get('username')?.value).toLowerCase();
    const password: string = this.usernameChangeForm.get('password')?.value;


    //const authToken = this.authService.getAuthToken();
//
    //if(authToken != null) {
    //  const userId: number = this.jwtHelper.decodeToken(authToken).userId
//
    //  this.requestsService.changeUsername(userId, username, password).subscribe({
    //      next: (result) => {
    //        this.usernameChangeForm.reset();
    //      }
    //    })
    //}

  }

}
