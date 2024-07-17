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
import { AbstractControl, ControlContainer, ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

import { NzSelectComponent } from 'ng-zorro-antd/select';

@Component({
  selector: 'vfm-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ClientSelectComponent),
      multi: true
    }
  ]
})
export class ClientSelectComponent implements OnInit, ControlValueAccessor {
  @ViewChild(NzSelectComponent, { static: false }) private select!: NzSelectComponent;

  @Input() public formControlName: string= '';
  @Input() public options: any[]= [];
  @Input() public allowClear = true;
  @Input() public showSearch = false;
  @Input() public placeHolder = '';
  @Input() public mode: 'multiple' | 'tags' | 'default' = 'default';

  public control: AbstractControl | null | undefined;

  constructor(
    @Host() @SkipSelf() @Optional() private controlContainer: ControlContainer,
    private changeDetector: ChangeDetectorRef
  ) {}

  public ngOnInit() {
    if (this.controlContainer) {
      this.control = this.controlContainer.control?.get(this.formControlName);
    }
  }

  public writeValue(value: string) {
    this.changeDetector.detectChanges();
    this.select.writeValue(value);
  }

  public registerOnChange(fn: () => void) {
    this.select.registerOnChange(fn);
  }

  public registerOnTouched(fn: () => void) {
    this.select.registerOnTouched(fn);
  }

  public setDisabledState(isDisabled: boolean) {
    this.select.setDisabledState(isDisabled);
  }
}
