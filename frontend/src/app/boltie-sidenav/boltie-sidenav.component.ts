import {Component, computed, Input, Signal, signal, WritableSignal} from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {MatListItem, MatListItemIcon, MatListItemTitle, MatNavList} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';
import {SidenavStateService} from '../sidenav-state.service';

export type MenuItem = {
  icon: string;
  label: string;
  route: string;
}

@Component({
  selector: 'app-boltie-sidenav',
  imports: [
    MatNavList,
    MatListItem,
    MatIcon,
    MatListItemIcon,
    MatListItemTitle
  ],
  templateUrl: './boltie-sidenav.component.html',
  styleUrl: './boltie-sidenav.component.scss'
})
export class BoltieSidenavComponent {

  constructor(public sidenavState: SidenavStateService) {
    this.sidenavState = sidenavState;
  }

  profilePicSize = computed(() => this.sidenavState.getState() ? '32' : '100');

  menuItems = signal<MenuItem[]>([
    {
      icon: 'home',
      label: 'Home',
      route: 'home'
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
