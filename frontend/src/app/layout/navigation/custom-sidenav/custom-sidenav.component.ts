import {Component, computed, inject, Input, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {MatListItem, MatListItemIcon, MatListItemTitle, MatNavList} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';
import {SidenavStateService} from '../../../services/sidenav/sidenav-state.service';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {MatDivider} from '@angular/material/divider';
import {AuthService} from '../../../services/auth.service';

export type MenuItem = {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-custom-sidenav',
  imports: [
    MatNavList,
    MatListItem,
    MatIcon,
    MatListItemIcon,
    MatListItemTitle,
    RouterLink,
    RouterLinkActive,
    MatDivider
  ],
  templateUrl: './custom-sidenav.component.html',
  styleUrl: './custom-sidenav.component.scss'
})
export class CustomSidenavComponent {

  currentUser = computed(() => this.authService.loginStateSignal() ?
    localStorage.getItem("username") : null);

  sidenavState = inject(SidenavStateService);
  private authService = inject(AuthService);

  profilePicSize = computed(() => this.sidenavState.getState() ? '32' : '100');

  menuItems = signal<MenuItem[]>([
    {
      icon: 'home',
      label: 'Home',
      route: ''
    },
    {
      icon: 'radio_button_checked',
      label: 'Live now',
      route: 'live'
    },
    {
      icon: 'smart_display',
      label: 'Videos',
      route: 'videos'
    },
    {
      icon: 'apps',
      label: 'Categories',
      route: 'categories'
    },
    {
      icon: 'history',
      label: 'History',
      route: 'history'
    },
    {
      icon: 'watch_later',
      label: 'Watch Later',
      route: 'watch-later'
    },
    {
      icon: 'help',
      label: 'About',
      route: 'about'
    },
  ]);

}
