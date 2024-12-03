import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidenavStateService {

  constructor() { }

  private isCollapsed = signal(false);

  get getState() {
    return this.isCollapsed;
  }

  toggleCollapse() {
    this.isCollapsed.update((isCollapsed) => !isCollapsed);
  }

}
