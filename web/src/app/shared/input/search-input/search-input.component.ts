import { Component, EventEmitter, Input, Output, OnInit, OnDestroy } from '@angular/core';

import { Subject, Subscription } from 'rxjs';
import { filter, debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'vfm-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.scss']
})
export class SearchInputComponent implements OnInit, OnDestroy {
  private value = new Subject<string>();

  @Input() public delay!: number;
  @Input() public placeholder: string="";
  @Input() public minInputLength!: number;
  subscriptions: Subscription[] = [];
  @Output() public input = new EventEmitter<string>();

  public onInput(e: Event) {
    e.stopPropagation();

    this.value.next((<HTMLInputElement>e.target).value);
  }

  public ngOnInit() {
    const sub = this.value
      .pipe(
        debounceTime(this.delay),
        filter((input) => input.length > this.minInputLength || input.length === 0),
        distinctUntilChanged()
      )
      .subscribe((value: string) => {
        this.input.emit(value);
      });

    this.subscriptions.push(sub);
  }

  onIconClick(event: HTMLInputElement) {
    this.input.emit(event.value);
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
