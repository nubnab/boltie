import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import {RouterOutlet} from "@angular/router";
import {BoltieSidenavComponent} from "../boltie-sidenav/boltie-sidenav.component";

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
}
