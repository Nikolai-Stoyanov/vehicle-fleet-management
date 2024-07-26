import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationService} from "../../auth/auth.service";

@Component({
  selector: 'vfm-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public currentUser:any;
  constructor(
    private httpClient: HttpClient,
    private authService: AuthenticationService
  ) {
    this.currentUser=this.authService.currentUserValue
  }

  ngOnInit() {
    console.log('HomeComponent ngOnInit');

  }
}
