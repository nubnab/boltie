import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import OvenPlayer from 'ovenplayer';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})


export class HomeComponent implements OnInit {

  private authService = inject(AuthService);

  ngOnInit() {

    const player = OvenPlayer.create('player_id', {
      sources: [{
          label: 'label_for_hls',
          type: 'hls',
          file: "http://192.168.1.2:9998/llhls.m3u8"
          //file: 'ws://192.168.1.2:3333/app/stream'
        }
      ]
    });
  }
  loginState = this.authService.loginStateSignal;


}
