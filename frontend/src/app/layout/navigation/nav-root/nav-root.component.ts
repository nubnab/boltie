import {Component, computed, inject} from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import {MatIconModule, MatIconRegistry} from '@angular/material/icon';
import {RouterLink, RouterOutlet} from "@angular/router";
import {CustomSidenavComponent} from "../custom-sidenav/custom-sidenav.component";
import {SidenavStateService} from '../../../services/sidenav/sidenav-state.service';
import {MatDialog} from '@angular/material/dialog';
import {UserFormComponent} from '../../../forms/user-form/user-form-component';
import {AuthService} from '../../../services/auth.service';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {DomSanitizer} from '@angular/platform-browser';

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
    MatMenuTrigger,
    MatMenu,
    MatMenuItem,
    RouterLink,
  ]
})
export class NavRootComponent {
  readonly dialog = inject(MatDialog);

  private sidenavState = inject(SidenavStateService);
  private authService = inject(AuthService);

  loginState = this.authService.loginStateSignal;
  sidenavWidth = computed(() =>
    this.sidenavState.getState() ? '60px' : '250px');

  constructor(private matIconRegistry: MatIconRegistry,
              private domSanitizer: DomSanitizer) {
    this.matIconRegistry.addSvgIcon(
      'boltie-logo',
      this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/favicon.svg')
    );
  }

  get getAuthService() {
    return this.authService;
  }

  toggleCollapse() {
    this.sidenavState.toggleCollapse();
  }

  openLoginModal() {
    const dialogRef = this.dialog.open(UserFormComponent, {
      id: 'login_dialog',
      enterAnimationDuration: 0,
      exitAnimationDuration: 0,
      data: {isLogin: true},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Login Dialog was closed.');
    })
  }

  openRegisterModal() {
    const dialogRef = this.dialog.open(UserFormComponent, {
      id: 'register_dialog',
      enterAnimationDuration: 0,
      exitAnimationDuration: 0,
      data: { isLogin: false },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog was closed.');
    })
  }
}
