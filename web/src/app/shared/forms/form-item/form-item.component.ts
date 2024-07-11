import { Component, OnInit, Input, TemplateRef } from '@angular/core';

@Component({
  selector: 'vfm-form-item',
  templateUrl: './form-item.component.html',
  styleUrls: ['./form-item.component.scss']
})
export class FormItemComponent implements OnInit {
  @Input() public label: string | undefined;
  @Input() public errorTip: string = '';
  @Input() public labelSpan: any;
  @Input() public controlSpan: any = '24';

  constructor() {}

  ngOnInit(): void {}
}
