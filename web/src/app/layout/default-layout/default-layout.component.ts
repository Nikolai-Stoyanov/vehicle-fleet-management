import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../auth/auth.service";

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
  public roles:any[]=[];

  constructor(
    private authService: AuthenticationService
  ) {
    this.currentUser=this.authService.currentUserValue
    // @ts-ignore
    // this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.roles = this.currentUser?.roles || [];
  }

  ngOnInit(): void {}

  public onLogoutClick(e: Event) {
    e.stopPropagation();
    this.authService.logout();
  }
}
