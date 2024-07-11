import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions';
import { NzSpaceModule } from 'ng-zorro-antd/space';
//
// import {
//   DatepickerModule,
//   TextInputModule,
//   NumberInputModule
// } from '@libs/input/src/lib';
// import { FormsModule as LibFormModule } from '@libs/forms/src/lib';
//
// import { ReferenceListModule } from '@shared/reference-list/reference-list.module';
// import { OwnerTypeModule } from '@shared/owner-type';
// import { DirectorateTypeModule } from '@shared/directorate-type';
// import { DrivingCategoryTypeModule } from '@shared/driving-category-type';

import { CarsBrandsFormComponent } from './cars-brands-form.component';

@NgModule({
  declarations: [CarsBrandsFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzGridModule,
    NzButtonModule,
    NzIconModule,
    NzCheckboxModule,
    NzInputModule,
    NzToolTipModule,
    NzDividerModule,
    NzCardModule,
    NzDescriptionsModule,
    NzSpaceModule,
    // TextInputModule,
    // LibFormModule,
    // DatepickerModule,
    // NumberInputModule,
    // ReferenceListModule,
    // OwnerTypeModule,
    // DirectorateTypeModule,
    // DrivingCategoryTypeModule
  ],
  exports: [CarsBrandsFormComponent],
  providers: [NzModalService]
})
export class CarsBrandsFormModule {}
