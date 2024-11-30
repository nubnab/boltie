import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavPageComponent} from './nav-page/nav-page.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavPageComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
