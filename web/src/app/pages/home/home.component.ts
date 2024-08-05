import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../auth/auth.service";

@Component({
  selector: 'vfm-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public currentUser:any;
  public roles:any[]=[];
  constructor(
    private authService: AuthenticationService
  ) {
    this.currentUser=this.authService.currentUserValue
    this.roles = this.currentUser?.roles || [];
  }

  ngOnInit() {
    console.log('ContactsComponent ngOnInit');

  }
}
