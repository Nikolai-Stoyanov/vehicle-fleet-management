import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersListModule } from './users-list/users-list.module';
import { UsersFormModule } from './users-form';
import { UsersRoutingModule } from './users.routing';
import {UsersService} from "./users.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    UsersListModule,
    UsersFormModule,
    UsersRoutingModule
  ],
  exports: [],
  providers: [UsersService]
})
export class UsersModule {}
