import {
  Host,
  Input,
  OnInit,
  Optional,
  SkipSelf,
  Component,
  ViewChild,
  forwardRef,
  ChangeDetectorRef
} from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  NG_VALUE_ACCESSOR
} from '@angular/forms';
import { NzDatePickerComponent } from 'ng-zorro-antd/date-picker';
import { en_US, NzI18nService } from 'ng-zorro-antd/i18n';

@Component({
  selector: 'vfm-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DatepickerComponent),
      multi: true
    }
  ]
})
export class DatepickerComponent implements OnInit, ControlValueAccessor {
  @ViewChild(NzDatePickerComponent, { static: false })
  public datepicker!: NzDatePickerComponent;

  @Input() public formControlName!: string;
  @Input() public format = 'dd.MM.yyyy HH:mm:ss';
  @Input() public id!: string;
  @Input() public showTime: any; // { nzFormat: 'HH:mm' }
  @Input() currentPickerValue!: any;
  @Input() disabled!: boolean;
  today = new Date();

  public control!: AbstractControl | null;

  constructor(
    @Host() @SkipSelf() @Optional() private controlContainer: ControlContainer,
    private changeDetector: ChangeDetectorRef,
    private i18n: NzI18nService
  ) {}

  public ngOnInit() {
    this.i18n.setLocale(en_US);
    if (this.controlContainer) {
      this.control =
        this.controlContainer.control?.get(this.formControlName) || null;
    }
  }

  public writeValue(value: any) {
    this.changeDetector.detectChanges();
    this.datepicker.writeValue(value);
  }

  public registerOnChange(fn: () => void) {
    this.datepicker.registerOnChange(fn);
  }

  public registerOnTouched(fn: () => void) {
    this.datepicker.registerOnTouched(fn);
  }

  public setDisabledState(isDisabled: boolean) {
    this.datepicker.setDisabledState(isDisabled);
  }
}
