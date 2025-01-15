import {Component, EventEmitter, inject, Output, signal} from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatDialog} from '@angular/material/dialog';
import {RequestsService} from '../../services/requests.service';

export type StreamKey = {
  key?: string
}

@Component({
  selector: 'app-stream-key-warning',
  imports: [
    MatButton
  ],
  templateUrl: './stream-key-warning.component.html',
  styleUrl: './stream-key-warning.component.scss'
})
export class StreamKeyWarningComponent {

  requestsService = inject(RequestsService);

  @Output() streamKeyInfo = new EventEmitter<string>();

  dialog = inject(MatDialog);

  dialogClose() {
    this.dialog.closeAll()
  }

  showKey() {
    this.requestsService.getStreamKey().subscribe((data: StreamKey) => {
      if(data.key != null) {
        this.streamKeyInfo.emit(data.key);
      }
    })
    this.dialogClose();
  }
}
