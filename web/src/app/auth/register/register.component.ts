import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Subscription} from 'rxjs';

import {NzMessageService} from 'ng-zorro-antd/message';

import {AuthenticationService} from '../auth.service';

@Component({
  selector: 'vfm-register',
  styleUrls: ['./register.component.scss'],
  templateUrl: './register.component.html',
  providers: [AuthenticationService],
})
export class RegisterComponent implements OnInit, OnDestroy {
  public subscriptions: Subscription[] = [];
  public registerForm: FormGroup;
  public passwordVisible = false;
  public loading = false;

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private authService: AuthenticationService
  ) {
    this.registerForm = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
      repeatPassword: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.required, Validators.email])
    });
  }

  ngOnInit() {
  }

  onSubmit(e: Event): void {
    e.stopPropagation();

    if (this.registerForm.value.password !== this.registerForm.value.repeatPassword) {
      this.registerForm.get('repeatPassword')?.setErrors({'incorrectRepeatPassword': true})
    }
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return
    }
    this.loading = true;
    const sub1 = this.authService.login(this.registerForm.value).subscribe(
      () => {
      },
      (error: any) => {
        this.message.error(error.status + ' ' + error.statusText);
      }
    );
    this.subscriptions.push(sub1);
    this.loading = false;
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
