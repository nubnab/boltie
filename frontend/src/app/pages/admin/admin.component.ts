import {Component, inject} from '@angular/core';
import {AsyncPipe} from '@angular/common';
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
  MatAutocompleteTrigger,
  MatOption
} from '@angular/material/autocomplete';
import {MatFormField} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RequestsService} from '../../services/requests.service';
import {map, Observable, startWith} from 'rxjs';
import {MatSelect} from '@angular/material/select';
import {MatIcon} from '@angular/material/icon';
import {MatButton} from '@angular/material/button';
import {MatSnackBar} from '@angular/material/snack-bar';

export type UserRoles = {
  id: number;
  username: string;
  role: string;
}

export type Role = {
  role: string;
}

@Component({
  selector: 'app-admin',
  imports: [
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    AsyncPipe,
    MatSelect,
    FormsModule,
    MatIcon,
    MatButton,
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  private requestsService = inject(RequestsService);
  private _snackBar = inject(MatSnackBar);

  userFormControl = new FormControl('');
  userRoles: UserRoles[] = [];
  filteredUsers: Observable<UserRoles[]> = new Observable<UserRoles[]>();
  selectedUser: UserRoles = { id: 0, username: '', role: '', };
  selectedRole: Role = { role: '', };

  roles: Role[] = [{role: "ROLE_ADMIN"}, {role: "ROLE_USER"}];

  displayUser(user: UserRoles): string {
    if(user.username !== undefined) {
      return `${user.username} ${user.role}`;
    }
    return '';
  }

  getUserRoles() {
    this.requestsService.getUserRoles().subscribe(userRoles => {
      this.userRoles = userRoles;
      this.initFilter();
    });
  }

  onUserSelected(event: MatAutocompleteSelectedEvent): void {
    this.selectedUser = event.option.value;
  }

  onSendPress() {
    this.requestsService.changeUserRole(this.selectedUser.id, this.selectedRole)
      .subscribe(res => console.log(res));
    this.openSnackBar(`User: ${this.selectedUser.username} now has ${this.selectedRole.role}`, 'close');
  }

  private openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {duration: 3000});
  }

  private _filter(value: string | UserRoles): UserRoles[] {
    if (typeof value !== 'string') {
      return this.userRoles;
    }
    let filteredValue = value.toLowerCase();

    return this.userRoles.filter(userRole =>
      userRole.username.toLowerCase().includes(filteredValue)
    );
  }

  private initFilter() {
    this.filteredUsers = this.userFormControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

}
