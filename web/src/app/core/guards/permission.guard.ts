import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { NzMessageService } from 'ng-zorro-antd/message';
import {AuthenticationService} from "../../auth/auth.service";

@Injectable({ providedIn: 'root' })
export class PermissionGuard {
  public currentUserRoles:any[]=[];
  constructor(
    public router: Router,
    private message: NzMessageService,
    private authService: AuthenticationService
  ) {
    this.currentUserRoles=this.authService.currentUserValue.roles;
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedPermission = route.data['expectedPermission'];
    const module = route.data['module'];

      let isAvailable = false;
      expectedPermission.forEach((item:any) => {
        if (this.currentUserRoles.includes(item)) {
          isAvailable = true;
        }
      });
      if (!isAvailable) {
        this.message.create('error', `You do not have rights for module ${module}!`);
        this.router.navigate(['/']);
        return false;
      }
      return isAvailable;
    }

}
