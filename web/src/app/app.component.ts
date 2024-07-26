import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import {AppConfigService} from "./app-config.service";
import {ZorroConfigModule} from "./zorro-config.module";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive,ZorroConfigModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'vehicleFleetManagement';
}
