import {Component, inject} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material/dialog';
import {StreamKeyWarningComponent} from '../../forms/stream-key-warning/stream-key-warning.component';

@Component({
  selector: 'app-settings',
  imports: [
    MatButton
  ],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {

  streamKeyInfo: string = "Show Key";

  dialog = inject(MatDialog);

  openWarningModal() {
    const dialogRef =
      this.dialog.open(StreamKeyWarningComponent);

    dialogRef.componentInstance.streamKeyInfo.subscribe((streamKeyInfo) => {
      this.streamKeyInfo = streamKeyInfo;
    })
  }
}
