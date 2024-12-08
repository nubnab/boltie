import {Component, computed, signal} from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import {RouterLink, RouterOutlet} from "@angular/router";
import {CustomSidenavComponent} from "../custom-sidenav/custom-sidenav.component";
import {SidenavStateService} from '../../../services/sidenav/sidenav-state.service';

@Component({
  selector: 'app-nav-test',
  templateUrl: './nav-root.component.html',
  styleUrl: './nav-root.component.scss',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    RouterOutlet,
    CustomSidenavComponent,
    RouterLink,
  ]
})
export class NavRootComponent {

  constructor(private sidenavState: SidenavStateService) {
    this.sidenavState = sidenavState;
  }

  toggleCollapse() {
    this.sidenavState.toggleCollapse();
  }

  sidenavWidth = computed(() => this.sidenavState.getState() ? '60px' : '250px');

}
