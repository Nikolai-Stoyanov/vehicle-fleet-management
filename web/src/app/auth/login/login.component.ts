import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Subscription} from 'rxjs';

import {NzMessageService} from 'ng-zorro-antd/message';

import {AuthenticationService} from '../auth.service';
import {Router} from "@angular/router";

@Component({
  selector: 'vfm-login',
  styleUrls: ['./login.component.scss'],
  templateUrl: './login.component.html',
  providers: [AuthenticationService],
})
export class LoginComponent implements OnInit, OnDestroy {
  public subscriptions: Subscription[] = [];
  public loginForm: FormGroup;
  public passwordVisible = false;
  public loading = false;

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
    });
  }

  ngOnInit() {}

  onSubmit(e: Event): void {
    e.stopPropagation();
    this.loading = true;


    if (this.loginForm.valid) {
      this.subscriptions.push( this.authService.login(this.loginForm.getRawValue()).subscribe(
        () => {
            this.router.navigate(['/home']).then(() => {
              window.location.reload();
            });
        },
        (error: any) => {
          console.log(error);
          this.message.error(error.status + " "+error.error.message);
        }
      ));
    }
    this.loading = false;
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
