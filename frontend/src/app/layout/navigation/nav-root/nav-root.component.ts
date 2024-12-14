import {Component, computed, Inject, inject, signal} from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import {RouterOutlet} from "@angular/router";
import {CustomSidenavComponent} from "../custom-sidenav/custom-sidenav.component";
import {SidenavStateService} from '../../../services/sidenav/sidenav-state.service';
import {MatDialog} from '@angular/material/dialog';
import {UserFormComponent} from '../../../forms/user-form/user-form-component';

@Component({
  selector: 'app-nav-root',
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
  ]
})
export class NavRootComponent {
  readonly dialog = inject(MatDialog);

  constructor(private sidenavState: SidenavStateService) {
    this.sidenavState = sidenavState;
  }

  toggleCollapse() {
    this.sidenavState.toggleCollapse();
  }

  sidenavWidth = computed(() => this.sidenavState.getState() ? '60px' : '250px');

  openTestLogin(): void {
    const dialogRef = this.dialog.open(UserFormComponent, {
      enterAnimationDuration: 0,
      exitAnimationDuration: 0,
      data: {isLogin: true},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Login Dialog was closed.');
    })
  }

  openTestRegister() {
    const dialogRef = this.dialog.open(UserFormComponent, {
      enterAnimationDuration: 0,
      exitAnimationDuration: 0,
      data: { isLogin: false },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed.');
    })
  }

}
