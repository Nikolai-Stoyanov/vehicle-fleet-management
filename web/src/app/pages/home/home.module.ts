import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';

import { NzSpaceModule } from 'ng-zorro-antd/space';
import { HomeRoutingModule } from './home.routing';
import {HomeComponent} from "./home.component";

@NgModule({
  exports: [],
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    NzLayoutModule,
    NzMenuModule,
    NzIconModule,
    NzToolTipModule,
    NzDropDownModule,
    NzSpaceModule,
    HomeRoutingModule,
  ],
})
export class HomeModule {}
