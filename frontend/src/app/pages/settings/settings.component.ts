import {Component, inject} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {StreamKeyWarningComponent} from '../../forms/stream-key-warning/stream-key-warning.component';
import {UsernameChangeComponent} from '../../forms/username-change/username-change.component';
import {RequestsService} from '../../services/requests.service';
import {AuthService} from '../../services/auth.service';

export type UsernameDto = {
  username: string;
}

@Component({
  selector: 'app-settings',
  imports: [
    MatButton
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {
  private requestsService = inject(RequestsService);
  private authService = inject(AuthService);

  streamKeyInfo: string = "Show Key";

  dialog = inject(MatDialog);

  get getAuthService() {
    return this.authService;
  }

  openWarningModal() {
    const dialogRef =
      this.dialog.open(StreamKeyWarningComponent);

    dialogRef.componentInstance.streamKeyInfo.subscribe((streamKeyInfo) => {
      this.streamKeyInfo = streamKeyInfo;
    })
  }

  openNameChangeModal() {
    this.requestsService.getUsernames().subscribe(res => {
      this.dialog.open(UsernameChangeComponent, {
        data: res,
      });
    })
  }
}
