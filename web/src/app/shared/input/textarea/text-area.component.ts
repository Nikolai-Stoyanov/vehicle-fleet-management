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
  DefaultValueAccessor,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'vfm-text-area',
  templateUrl: './text-area.component.html',
  styleUrls: ['./text-area.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextAreaComponent),
      multi: true
    }
  ]
})
export class TextAreaComponent implements OnInit, ControlValueAccessor {
  @ViewChild(DefaultValueAccessor, { static: false }) private valueAccessor!: DefaultValueAccessor;

  @Input() public id!: string;
  @Input() public placeholder!: string;
  @Input() public formControlName!: string;
  @Input() public autosize!: string | boolean | { minRows: number; maxRows: number };
  @Input() readonly!: boolean;
  public control: AbstractControl | null | undefined;

  constructor(
    @Host() @SkipSelf() @Optional() private controlContainer: ControlContainer,
    private changeDetector: ChangeDetectorRef
  ) {}

  public ngOnInit() {
    if (this.controlContainer) {
      this.control = this.controlContainer?.control?.get(this.formControlName);
    }
  }

  public writeValue(value: string) {
    this.changeDetector.detectChanges();
    this.valueAccessor.writeValue(value);
  }

  public registerOnChange(fn: (_: any) => object) {
    this.changeDetector.detectChanges();
    this.valueAccessor.registerOnChange(fn);
  }

  public registerOnTouched(fn: () => void) {
    this.changeDetector.detectChanges();
    this.valueAccessor.registerOnTouched(fn);
  }

  public setDisabledState(isDisabled: boolean) {
    this.changeDetector.markForCheck();
    this.valueAccessor.setDisabledState(isDisabled);
  }
}
