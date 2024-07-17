import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FuelProviderRoutingModule } from './fuel.routing';
import {FuelComponent} from "./fuel.component";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {FuelListModule} from "./fuel/fuel-list/fuel-list.module";
import {FuelProviderListModule} from "./fuel-provider/fuel-provider-list/fuel-provider-list.module";
import {FuelService} from "./fuel.service";
import {NzModalService} from "ng-zorro-antd/modal";
import {NzDividerComponent} from "ng-zorro-antd/divider";

@NgModule({
  declarations: [FuelComponent],
  imports: [
    CommonModule,
    FuelProviderRoutingModule,
    NzRowDirective,
    NzColDirective,
    FuelListModule,
    FuelProviderListModule,
    NzDividerComponent
  ],
  exports: [FuelComponent],
  providers: [FuelService,NzModalService]
})
export class FuelModule {}
