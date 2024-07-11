import {
  Component,
  Input,
  Output,
  EventEmitter,
  ChangeDetectionStrategy,
  TemplateRef,
  ContentChild,
  ElementRef,
  SimpleChanges,
  OnInit,
  OnChanges
} from '@angular/core';
import { TableColumnInterface } from './dummy-table-checkboxes';

import { Subscription } from 'rxjs';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'lib-dummy-table-checkboxes',
  templateUrl: './dummy-table-checkboxes.component.html',
  styleUrls: ['./dummy-table-checkboxes.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DummyTableCheckboxesComponent implements OnInit, OnChanges {
  @Input() public tableHeight: string = '64vh';
  @Input() public loading: boolean = false;
  @Input() public allItems: any;
  public currentItems: any;
  @Input() public haveCheckboxes: boolean = false;
  @Input() public haveEdit: boolean = false;
  @Input() public listOfColumns!: TableColumnInterface[];
  @Output() public onItemClick = new EventEmitter<any>();
  @Output() public onItemDblClick = new EventEmitter<any>();
  @Output() public setOfCheckedItem = new EventEmitter<any>();
  public subscription: Subscription = new Subscription();
  public setOfCheckedId = new Set<string>();
  public checked = false;
  public indeterminate = false;
  public currentTableColumns: TableColumnInterface[] = [];
  titleTemplate: string | undefined

  public searchValue: any = null;
  public visible = false;

  @ContentChild('actionTemplate') actionTemplate!: TemplateRef<ElementRef>;
  @ContentChild('concatenationTemplate') concatenationTemplate!: TemplateRef<
    ElementRef
  >;
  constructor(private message: NzMessageService) {}

  ngOnInit(): void {
    this.currentItems = this.allItems;
    this.currentTableColumns = [];
    this.listOfColumns.forEach((column: TableColumnInterface) => {
      if (column.visible === true) {
        this.currentTableColumns.push(column);
      }
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['allItems'] && !changes['allItems'].firstChange) {
      this.allItems = changes['allItems'].currentValue;
      this.currentItems = this.allItems;
    }

    if (changes['listOfColumns'] && !changes['listOfColumns'].firstChange) {
      this.currentTableColumns = [];
      this.listOfColumns = changes['listOfColumns'].currentValue;
      this.listOfColumns.forEach((column: TableColumnInterface) => {
        if (column.visible === true) {
          this.currentTableColumns.push(column);
        }
      });
    }
  }

  changeColumns(e: any) {
    this.currentTableColumns = e;
  }

  trackByFn(index: any, item: any) {
    return index;
  }

  onClick(item: any) {
    this.onItemClick.emit(item);
  }

  onDblClick(item: any) {
    this.onItemDblClick.emit(item);
  }

  public updateCheckedSet(id: string, checked: boolean): void {
    if (checked && !this.setOfCheckedId.has(id)) {
      this.setOfCheckedId.add(id);
    } else {
      this.setOfCheckedId.delete(id);
    }
    const arr: string[] = [];
    this.setOfCheckedId.forEach((id: string) => {
      arr.push(id);
    });
    this.setOfCheckedItem.emit(arr);
  }

  public onItemChecked(id: string, checked: boolean): void {
    this.updateCheckedSet(id, checked);
    this.refreshCheckedStatus();
  }

  public onAllChecked(value: boolean): void {
    this.currentItems.forEach((item: any) => {
      this.updateCheckedSet(item.id, value);
    });

    this.refreshCheckedStatus();
  }

  public refreshCheckedStatus(): void {
    this.checked =
      this.currentItems.every((item: any) =>
        this.setOfCheckedId.has(item.id)
      ) && this.currentItems.length > 0;
    this.indeterminate =
      this.currentItems.some((item: any) => this.setOfCheckedId.has(item.id)) &&
      !this.checked;
  }

  search(query?: any): void {
    this.currentItems = [];
    if (query) {
      const forSearch = query.trim().toLowerCase();
      this.currentItems = this.allItems.filter((object: any) => {
        return (
          JSON.stringify(object)
            .toString()
            .toLowerCase()
            .indexOf(forSearch) !== -1
        );
      });
    } else {
      this.currentItems = this.allItems;
    }
  }
}
