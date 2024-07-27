import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

export interface MenuItem {
  title: string;
  open?: boolean;
  icon?: any;
  disable?: boolean;
  selected?: boolean;
  routerLink?: string | string[];
  children?: any;
  permission?: string | string[];
}

@Component({
  selector: 'vfm-menu',
  templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit {
  public model: MenuItem[] = [
    {
      title: $localize`Cars`,
      icon: 'car',
      permission: 'ALL',
      children: [
        {
          title: $localize`Car record`,
          icon: 'car',
          routerLink: ['/car-record'],permission: 'ALL',
        }
      ]
    },
    {
      title: $localize`Operations`,
      icon: 'audit',
      permission: 'ALL',
      children: [
        {
          title: $localize`Declaration`,
          icon: 'file',
          routerLink: '/declarations',permission: 'ALL',
        }
      ]
    },
    {
      title: $localize`Nomenclatures`,
      icon: 'build',
      permission: 'ALL',
      children: [
        {
          title: $localize`Car brands`,
          icon: 'trademark',
          routerLink: '/car-brands',
          permission: 'ALL',
        },
        {
          title: $localize`Car models`,
          icon: 'car',
          routerLink: '/car-models',
          permission: 'ALL',
        },
        {
          title: $localize`Fuel`,
          icon: 'shop',
          routerLink: '/fuel',
          permission: 'ALL',
        },
        {
          title: $localize`Users`,
          icon: 'usergroup-add',
          routerLink: '/users',
          permission: 'ADMIN',
        }
      ]
    },
  ];
  public openMap: { [name: string]: boolean } = {
    Cars: false,
    Operations: false,
    Nomenclatures: false,
  };

  @Input() public mode = 'vertical';
  @Input() public isCollapsed = false;
  @Input() roles!: any[];
  constructor(
    private router: Router
  ) {
  }

  public ngOnInit() {
    setTimeout(() => {
      this.initialOpenMenuHandler();
    }, 0);
  }

  openHandler(value: string): void {
    for (const key in this.openMap) {
      this.openMap[key] = key === value;
    }
  }

  initialOpenMenuHandler() {
    const url = this.router.url;
 if (url.includes('car-record')) {
      this.openMap[$localize`Cars`] = true;
    } else if ( url.includes('declarations')) {
      this.openMap[$localize`Operations`] = true;
    } else if (
      url.includes('car-models') ||
      url.includes('car-brands') ||
      url.includes('fuel')||
      url.includes('users')
    ) {
      this.openMap[$localize`Nomenclatures`] = true;
    }
  }
}
