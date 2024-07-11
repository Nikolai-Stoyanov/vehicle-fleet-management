import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Subscription} from 'rxjs';

import {NzMessageService} from 'ng-zorro-antd/message';

import {AuthenticationService} from '../auth.service';

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
    private authService: AuthenticationService
  ) {
    this.loginForm = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
      language: this.fb.control('', [Validators.required]),
      appId: this.fb.control(''),
      redirectTo: this.fb.control('')
    });
  }

  ngOnInit() {}

  onSubmit(e: Event): void {
    e.stopPropagation();
    this.loading = true;

    if (this.loginForm.valid) {
      const sub1 = this.authService.login(this.loginForm.value).subscribe(
        () => {
        },
        (error: any) => {
          console.log(error);
          this.message.error(error.statusText);
        }
      );
      this.subscriptions.push(sub1);
    }
    this.loading = false;
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
