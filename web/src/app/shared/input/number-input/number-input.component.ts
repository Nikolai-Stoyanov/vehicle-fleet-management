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
import { NzInputNumberComponent } from 'ng-zorro-antd/input-number';

@Component({
  selector: 'vfm-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => NumberInputComponent),
      multi: true
    }
  ]
})
export class NumberInputComponent implements OnInit, ControlValueAccessor {
  @ViewChild(NzInputNumberComponent, { static: false })
  public input!: NzInputNumberComponent;

  @Input() public id!: string;
  @Input() public placeholder!: string;
  @Input() public formControlName!: string;
  @Input() public min: number = Number.MIN_SAFE_INTEGER;
  @Input() public max: number = Number.MAX_SAFE_INTEGER;
  @Input() public step: number = 1;
  @Input() public formatterType!: string;
  @Input() public disabled!: boolean;

  public control!: AbstractControl | null;

  constructor(
    @Host() @SkipSelf() @Optional() private controlContainer: ControlContainer,
    private changeDetector: ChangeDetectorRef
  ) {}

  public ngOnInit() {
    if (this.controlContainer) {
      this.control =
        this.controlContainer.control?.get(this.formControlName) || null;
      this.changeDetector.detectChanges();
    }
  }

  public writeValue(value: number) {
    this.changeDetector.detectChanges();
    this.input.writeValue(value);
  }

  public registerOnChange(fn: (_: number) => void) {
    this.changeDetector.detectChanges();
    this.input.registerOnChange(fn);
  }

  public registerOnTouched(fn: () => void) {
    this.changeDetector.detectChanges();
    this.input.registerOnTouched(fn);
  }

  public setDisabledState(isDisabled: boolean) {
    this.changeDetector.markForCheck();
    this.input.setDisabledState(isDisabled);
  }
}
