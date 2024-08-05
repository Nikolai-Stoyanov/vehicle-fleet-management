import {Component, OnInit, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Subscription} from 'rxjs';

import {NzMessageService} from 'ng-zorro-antd/message';

import {AuthenticationService} from '../auth.service';
import {Router} from "@angular/router";

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
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required]),
      confirmPassword: this.fb.control('', [Validators.required]),
      email: this.fb.control('', [Validators.required, Validators.email])
    });
  }

  ngOnInit() {
  }

  onSubmit(e: Event): void {
    e.stopPropagation();

    if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
      this.registerForm.get('confirmPassword')?.setErrors({'incorrectRepeatPassword': true})
    }
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return
    }
    this.loading = true;


    this.subscriptions.push(this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          this.router.navigate(['/login']).then();
        },
        error: (error: any) => {
          this.message.error(error.status + ' ' + error.error.message);
        }
      }
    ));
    this.loading = false;
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
