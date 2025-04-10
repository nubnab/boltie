import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidenavStateService {
  private isCollapsed = signal(false);

  constructor() { }

  get getState() {
    return this.isCollapsed;
  }

  toggleCollapse() {
    this.isCollapsed.update((isCollapsed) => !isCollapsed);
  }

}
