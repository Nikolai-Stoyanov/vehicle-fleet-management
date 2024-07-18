import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DeclarationsListModule } from './declarations-list/declarations-list.module';
import { DeclarationsFormModule } from './declarations-form';
import { DeclarationsRoutingModule } from './declarations.routing';
import {DeclarationsService} from "./declarations.service";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    DeclarationsListModule,
    DeclarationsFormModule,
    DeclarationsRoutingModule
  ],
  exports: [],
  providers: [DeclarationsService]
})
export class DeclarationsModule {}
