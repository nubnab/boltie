import {Component, computed, signal} from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import {RouterOutlet} from "@angular/router";
import {BoltieSidenavComponent} from "../boltie-sidenav/boltie-sidenav.component";
import {SidenavStateService} from '../sidenav-state.service';

@Component({
  selector: 'app-nav-test',
  templateUrl: './nav-test.component.html',
  styleUrl: './nav-test.component.scss',
  standalone: true,
    imports: [
        MatToolbarModule,
        MatButtonModule,
        MatSidenavModule,
        MatListModule,
        MatIconModule,
        RouterOutlet,
        BoltieSidenavComponent,
    ]
})
export class NavTestComponent {

  constructor(private sidenavState: SidenavStateService) {
    this.sidenavState = sidenavState;
  }

  toggleCollapse() {
    this.sidenavState.toggleCollapse();
  }

  sidenavWidth = computed(() => this.sidenavState.getState() ? '64px' : '250px');

}
