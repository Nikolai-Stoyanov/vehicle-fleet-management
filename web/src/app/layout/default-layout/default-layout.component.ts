import { Component, OnInit } from '@angular/core';

// import { AppConfigService } from '@core/services/app-config.service';
// import { AuthenticationService } from '@auth/auth.service';

@Component({
  selector: 'vfm-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss']
})
export class DefaultLayoutComponent implements OnInit {
  public isCollapsed = true;
  public currentUser:any;
  public appConfig:any;

  constructor(
    // public appConfigService: AppConfigService,
    // private authService: AuthenticationService
  ) {
    // this.currentUser = this.authService.currentUserValue;
    // this.appConfig = this.appConfigService.config;
  }

  ngOnInit(): void {}

  public onLogoutClick(e: Event) {
    e.stopPropagation();

    // this.authService.invalidateToken().subscribe();
    // this.authService.logoutFromApp();
  }
}
