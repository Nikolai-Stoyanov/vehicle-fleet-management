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
      title: `Cars`,
      icon: 'car',
      children: [
        {
          title: `Car record`,
          icon: 'car',
          routerLink: ['/cars']
        }
      ]
    },
    {
      title: `Operations`,
      icon: 'audit',
      children: [
        {
          title: `Declaration`,
          icon: 'file',
          routerLink: '/declarations'
        }
      ]
    },
    {
      title: `Nomenclatures`,
      icon: 'build',
      children: [
        {
          title: `Car brands`,
          icon: 'trademark',
          routerLink: '/car-brands'
        },
        {
          title: `Car models`,
          icon: 'car',
          routerLink: '/car-models'
        },
        {
          title: `Fuel`,
          icon: 'shop',
          routerLink: '/fuel'
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

  constructor(
    private router: Router
  ) {}

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
 if (url.includes('cars')) {
      this.openMap[`Cars`] = true;
    } else if ( url.includes('declarations')) {
      this.openMap[`Operations`] = true;
    } else if (
      url.includes('car-models') ||
      url.includes('car-brands') ||
      url.includes('fuel')
    ) {
      this.openMap[`Nomenclatures`] = true;
    }
  }
}
