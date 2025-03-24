import {Component} from '@angular/core';
import {NavRootComponent} from './layout/navigation/nav-root/nav-root.component';

@Component({
  selector: 'app-root',
  imports: [NavRootComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'boltie';
}
