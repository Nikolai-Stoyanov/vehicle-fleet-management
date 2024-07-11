import { NzTableFilterFn, NzTableSortFn } from 'ng-zorro-antd/table';

export interface TableColumnInterface {
  id: string;
  title: string;
  propsName: string;
  width: string;
  type: string;
  icon?: string;
  visible?: boolean;
  key?: string;
  link?: string;
  tooltip?: boolean;
  align?: 'center' | 'left' | 'right' | null;
  concatProp?: string;
  right?: boolean;
  filter?: boolean;
  showSortFn?: boolean;
  sortFn?: NzTableSortFn | null;
  filterFn?: NzTableFilterFn | null;
  listOfFilter?: any;
}
