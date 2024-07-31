import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';

import { NzSpaceModule } from 'ng-zorro-antd/space';
import { HomeRoutingModule } from './about-us.routing';
import {AboutUsComponent} from "./about-us.component";

@NgModule({
  exports: [AboutUsComponent],
  declarations: [AboutUsComponent],
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
export class AboutUsModule {}
