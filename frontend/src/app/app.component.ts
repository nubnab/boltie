import {Component, signal} from '@angular/core';
import {NavTestComponent} from './nav-test/nav-test.component';


@Component({
  selector: 'app-root',
  imports: [NavTestComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';



}
