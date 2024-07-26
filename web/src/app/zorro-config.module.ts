import {inject, LOCALE_ID} from '@angular/core';
import {NzConfig, NZ_CONFIG} from 'ng-zorro-antd/core/config';
import {NgModule, Component, ViewChild, TemplateRef, Injector, ComponentFactoryResolver} from '@angular/core';
import {registerLocaleData} from '@angular/common';
import en from '@angular/common/locales/en';
import bg from '@angular/common/locales/en';

registerLocaleData(en);
registerLocaleData(bg);

import {NZ_I18N, bg_BG, en_US, NZ_DATE_LOCALE, NZ_DATE_CONFIG} from 'ng-zorro-antd/i18n';

@Component({
  template: `
    <ng-template #defaultEmpty>
      <div style="text-align: center;">
        <img src="../assets/images/empty.svg"/>
        <p i18n>No data</p>
      </div>
    </ng-template>
  `
})
export class GlobalTemplatesComponent {
  @ViewChild('defaultEmpty', {static: true}) defaultEmpty!: TemplateRef<any>;
}

const nzConfigFactory = (injector: Injector, resolver: ComponentFactoryResolver): NzConfig => {
  const factory = resolver.resolveComponentFactory(GlobalTemplatesComponent);
  const {defaultEmpty} = factory.create(injector).instance;
  return {
    empty: {nzDefaultEmptyContent: defaultEmpty}
  };
};


@NgModule({
  declarations: [GlobalTemplatesComponent],
  providers: [
    {
      provide: NZ_I18N,
      useFactory: () => {
        const localId = inject(LOCALE_ID);
        switch (localId) {
          case 'en':
            return en_US;
          case 'bg':
            return bg_BG;
          default:
            return en_US;
        }
      }
    },
    {provide: NZ_CONFIG, useFactory: nzConfigFactory, deps: [Injector, ComponentFactoryResolver]},
    {
      provide: NZ_DATE_CONFIG,
      useValue: {
        firstDayOfWeek: 1 // week starts on Monday (Sunday is 0)
      }
    }
  ]
})
export class ZorroConfigModule {
}
