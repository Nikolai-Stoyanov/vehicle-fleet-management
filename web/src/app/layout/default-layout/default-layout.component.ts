import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'vfm-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss']
})
export class DefaultLayoutComponent implements OnInit {
  public currentUser:any;
  public roles:any[]=[];

  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.currentUser=this.authService.currentUserValue
    this.roles = this.currentUser?.roles || [];
  }

  ngOnInit(): void {}

  isSelected(route: string): boolean {
    return route === this.router.url;
  }

  public onLogoutClick(e: Event) {
    e.stopPropagation();
    this.authService.logout();
  }

  changeSelection($event: Event) {
    console.log($event)
  }
}
