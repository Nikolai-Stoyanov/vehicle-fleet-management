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
import differenceInCalendarDays from 'date-fns/differenceInCalendarDays';
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
  @Input() disabledDates!: boolean;
  @Input() disabledDatesAfterToday!: boolean;
  @Input() disabled!: boolean;
  // tomorrow = new Date().setDate(new Date().getDate() + 1);
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

  public disabledDate = (current: Date): boolean => {
    if (this.disabledDates) {
      if (!current || !this.currentPickerValue?.value) {
        if (this.disabledDatesAfterToday) {
          return current > this.today;
        }
        return false;
      }
      if (this.currentPickerValue && this.currentPickerValue.value) {
        if (this.currentPickerValue.picker === 'startDate') {
          if (this.disabledDatesAfterToday) {
            return (
              differenceInCalendarDays(
                current,
                new Date(this.currentPickerValue.value)
              ) < 0 ||
              differenceInCalendarDays(current, new Date(this.today)) > 0
            );
          }
          return (
            differenceInCalendarDays(
              current,
              new Date(this.currentPickerValue.value)
            ) < 0
          );
        }

        return (
          differenceInCalendarDays(
            current,
            new Date(this.currentPickerValue.value)
          ) > 0
        );
      }
      return false;
    }
    return false;
  };

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
