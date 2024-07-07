import { Component, OnInit } from '@angular/core';

@Component({
  template: '<vfm-default-layout></vfm-default-layout>'
})
export class LayoutComponent implements OnInit {
  constructor() {}

  ngOnInit() {
    console.log('LayoutComponent ngOnInit');
  }
}
