import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CarBrandItem } from './car-brands';

@Injectable({
  providedIn: 'root'
})
export class CarBrandsService {
  private readonly endpoint = '';

  public carBrandsListColumns  = [
    {
      id: '1',
      title: 'ID',
      propsName: 'id',
      width: '70px',
      type: 'text',
      visible: true,
      align: 'center',
      sortFn: (a: any, b: any) => a.id - b.id,
      showSortFn: true
    },
    {
      id: '2',
      title: 'Марка',
      propsName: 'mark',
      width: '170px',
      sortFn: (a: any, b: any) => a.mark.localeCompare(b.mark),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '3',
      title: 'Модел',
      propsName: 'model',
      width: '170px',
      sortFn: (a: any, b: any) => a.model.localeCompare(b.model),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '4',
      title: 'Година',
      propsName: 'manufactureYear',
      width: '170px',
      sortFn: (a: any, b: any) =>
        a.manufactureYear.localeCompare(b.manufactureYear),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '5',
      title: 'Производител',
      propsName: 'manufacturer',
      width: '170px',
      sortFn: (a: any, b: any) => a.manufacturer.localeCompare(b.manufacturer),
      showSortFn: true,
      type: 'text',
      visible: true,
      align: 'center'
    },
    {
      id: '6',
      title: 'Статус',
      propsName: 'active',
      width: '150px',
      sortFn: (a: any, b: any) => Number(a.active) - Number(b.active),
      showSortFn: true,
      type: 'action',
      visible: true,
      align: 'center',
      right: true,
      filter: true,
      listOfFilter: [
        { value: true, text: 'активен' },
        { value: false, text: 'неактивен' }
      ],
      filterFn: (list: string[], item: any) =>
        list.some(active => item.active === active)
    }
  ];

  public carBrandsListData: CarBrandItem[] = [
    {
      id: 1,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2012',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 2,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2013',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 3,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2014',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 4,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2015',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 5,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2016',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 6,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2018',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 7,
      mark: 'Skoda',
      model: 'Octavia',
      manufactureYear: '2019',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 8,
      mark: 'Skoda',
      model: '110850',
      manufactureYear: '1992',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 9,
      mark: 'Skoda',
      model: 'Europe',
      manufactureYear: '1989',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 10,
      mark: 'Skoda',
      model: 'Madara',
      manufactureYear: '1998',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 11,
      mark: 'Skoda',
      model: 'Citigo',
      manufactureYear: '2017',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 12,
      mark: 'Skoda',
      model: 'Citigo',
      manufactureYear: '2019',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 13,
      mark: 'Skoda',
      model: 'Superb',
      manufactureYear: '2004',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 14,
      mark: 'Skoda',
      model: 'Superb',
      manufactureYear: '2017',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 15,
      mark: 'Skoda',
      model: 'Fabia',
      manufactureYear: '2015',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 16,
      mark: 'Skoda',
      model: 'Fabia',
      manufactureYear: '2016',
      manufacturer: 'SKODA',
      active: true
    },
    {
      id: 17,
      mark: 'Ford',
      model: 'Pritsche',
      manufactureYear: '2008',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 18,
      mark: 'Ford',
      model: 'Ranger',
      manufactureYear: '2003',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 19,
      mark: 'Ford',
      model: 'Ranger',
      manufactureYear: '2009',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 20,
      mark: 'Ford',
      model: 'Ranger',
      manufactureYear: '2010',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 21,
      mark: 'Ford',
      model: 'Ranger',
      manufactureYear: '2011',
      manufacturer: 'Ford',
      active: true
    },

    {
      id: 22,
      mark: 'Ford',
      model: 'Transit',
      manufactureYear: '2007',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 23,
      mark: 'Ford',
      model: 'Transit',
      manufactureYear: '2008',
      manufacturer: 'Ford',
      active: true
    },
    {
      id: 24,
      mark: 'Ford',
      model: 'Transit',
      manufactureYear: '2009',
      manufacturer: 'Ford',
      active: true
    },

    {
      id: 25,
      mark: 'Dacia',
      manufactureYear: '2017',
      model: 'Duster',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 26,
      mark: 'Dacia',
      manufactureYear: '2018',
      model: 'Duster',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 27,
      mark: 'Dacia',
      manufactureYear: '2019',
      model: 'Duster',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 28,
      mark: 'Dacia',
      manufactureYear: '2017',
      model: 'Dokker',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 29,
      mark: 'Dacia',
      manufactureYear: '2018',
      model: 'Dokker',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 30,
      mark: 'Dacia',
      manufactureYear: '2019',
      model: 'Dokker',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 31,
      mark: 'Dacia',
      manufactureYear: '2020',
      model: 'Dokker',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 32,
      mark: 'Dacia',
      manufactureYear: '2021',
      model: 'Dokker',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 33,
      mark: 'Dacia',
      manufactureYear: '2007',
      model: 'Logan',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 34,
      mark: 'Dacia',
      manufactureYear: '2008',
      model: 'Logan',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 35,
      mark: 'Dacia',
      manufactureYear: '2015',
      model: 'Logan',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 36,
      mark: 'Dacia',
      manufactureYear: '2018',
      model: 'Logan',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 37,
      mark: 'Dacia',
      manufactureYear: '2018',
      model: 'Lodgy',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 38,
      mark: 'Dacia',
      manufactureYear: '2018',
      model: 'Sandero',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 39,
      mark: 'Toyota',
      manufactureYear: '2008',
      model: 'Hilux',
      manufacturer: 'Dacia',
      active: true
    },
    {
      id: 40,
      mark: 'Toyota',
      manufactureYear: '2012',
      model: 'Hilux',
      manufacturer: 'Dacia',
      active: true
    }
  ];

  constructor(private http: HttpClient) {}
  // columns
  public getColumns(): Observable<any> {
    return of(this.carBrandsListColumns);
  }

  // data

  fetchLatest(): Observable<any> {
    return of(this.carBrandsListData);
  }
  // fetchLatest(): Observable<any> {
  //   return this.http.get<any>(this.endpoint);
  // }
}
