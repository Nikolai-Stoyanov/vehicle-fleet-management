import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';

import {NzModalRef, NzModalService} from 'ng-zorro-antd/modal';
import {NzMessageService} from 'ng-zorro-antd/message';
import {CarRecordService} from '../car-record.service';
import {ReferenceListComponent} from "../../../shared/reference-list/reference-list.component";
import {CarRecord, DrivingCategoryType, Fuels, Headquarter, Owner, VehicleType} from "../car-record";
import {
  DriversService, FuelCardService,
  RegistrationNumberService, ResponsibleService,
} from "../../../shared/services";
import {Subscription} from "rxjs";

@Component({
  selector: 'vfm-car-record-form',
  templateUrl: './car-record-form.component.html',
  styleUrls: ['./car-record-form.component.scss'],
})
export class CarRecordFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  @Input() public currentItemId: any;
  @Output() public submit = new EventEmitter<any>();
  public currentItem: any;
  public fuelOptions: any;
  public categoryOptions: any[] = [];
  public headquartersOptions: any[] = [];
  public ownerNameOptions: any[] = [];
  public vehicleOptions: any[] = [];
  public subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private modalService: NzModalService,
    private modal: NzModalRef,
    private message: NzMessageService,
    private svc: CarRecordService,
    private registrationNumberService: RegistrationNumberService,
    private responsibleService: ResponsibleService,
    private driversService: DriversService,
    private fuelCardService: FuelCardService,
  ) {
    this.currentItemId = this.modal['config'].nzData.id;
  }

  public ngOnInit(): void {
    this.getOptions()
    if (this.currentItemId) {
      this.svc.fetchRecordById(this.currentItemId).subscribe((res: CarRecord) => {
        this.currentItem = res;
        this.getForm()
      });
    } else {
      this.getForm()
    }
  }

  private getForm() {
    this.form = this.fb.group({
      id: this.fb.control({
        value: this.currentItem?.id || null,
        disabled: true,
      }),
      registrationNumber: this.fb.control(
        this.currentItem?.registrationNumber || null,
        Validators.required
      ),
      category: this.fb.control(
        this.currentItem?.category || null,
        Validators.required
      ),
      firstRegistration: this.fb.control(
        this.currentItem?.firstRegistration || null,
        Validators.required
      ),
      vehicleType: this.fb.control(
        this.currentItem?.vehicleType || null,
        Validators.required
      ),
      description: this.fb.control(
        this.currentItem?.description || null,
        Validators.required
      ),
      brand: this.fb.control(
        this.currentItem?.brand || null,
        Validators.required
      ),
      model: this.fb.control(
        this.currentItem?.model || null,
        Validators.required
      ),
      seatingCapacity: this.fb.control(
        this.currentItem?.seatingCapacity || null,
        Validators.required
      ),
      frameNumber: this.fb.control(
        this.currentItem?.frameNumber || null,
        Validators.required
      ),
      engineNumber: this.fb.control(
        this.currentItem?.engineNumber || null,
        Validators.required
      ),
      horsePower: this.fb.control(
        this.currentItem?.horsePower || null,
        Validators.required
      ),
      enginePower: this.fb.control(
        this.currentItem?.enginePower || null,
        Validators.required
      ),
      engineVolume: this.fb.control(
        this.currentItem?.engineVolume || null,
        Validators.required
      ),
      primaryColor: this.fb.control(
        this.currentItem?.primaryColor || null,
        Validators.required
      ),
      additionalColor: this.fb.control(
        this.currentItem?.additionalColor || null,
        Validators.required
      ),
      loadCapacity: this.fb.control(
        this.currentItem?.loadCapacity || null,
        Validators.required
      ),
      ownerName: this.fb.control(
        this.currentItem?.ownerName || null,
        Validators.required
      ),
      department: this.fb.control(
        this.currentItem?.department || null,
        Validators.required
      ),
      headquarters: this.fb.control(
        this.currentItem?.headquarters || null,
        Validators.required
      ),
      stay: this.fb.control(
        this.currentItem?.stay || null,
        Validators.required
      ),
      totalMileage: this.fb.control(
        this.currentItem?.totalMileage || null,
        Validators.required
      ),
      developmentFromMileage: this.fb.control(
        this.currentItem?.developmentFromMileage || null,
        Validators.required
      ),
      developmentToMileage: this.fb.control(
        this.currentItem?.developmentToMileage || null,
        Validators.required
      ),
      inspectionDateTo: this.fb.control(
        this.currentItem?.inspectionDateTo || null,
        Validators.required
      ),
      vignetteDateTo: this.fb.control(
        this.currentItem?.vignetteDateTo || null,
        Validators.required
      ),
      insuranceDateTo: this.fb.control(
        this.currentItem?.insuranceDateTo || null,
        Validators.required
      ),
      status: this.fb.control(
        this.currentItem?.status || null,
        Validators.required
      ),
      fuels: this.fb.array(
        this.currentItem?.fuelOptions
          ? this.currentItem.fuels.map((options: any) =>
            this.fb.group({
              type: this.fb.control(options.type || null,
                Validators.required),
              tankVolume: this.fb.control(options.tankVolume || null,
                Validators.required),
              distanceLimit: this.fb.control(options.distanceLimit || null),
              moneyLimit: this.fb.control(options.moneyLimit || null)
            })
          )
          : []
      ),
      createdBy: this.fb.control(
        this.currentItem?.createdBy || null,
        Validators.required
      ),
      createdDate: this.fb.control(
        this.currentItem?.createdDate || null,
        Validators.required
      ),
      modifyBy: this.fb.control(
        this.currentItem?.modifyBy || null,
        Validators.required
      ),
      modifyDate: this.fb.control(
        this.currentItem?.modifyDate || null,
        Validators.required
      ),
    });
  }

  get getFuelOptionsControls(): any {
    return (this.form.get('fuels') as FormArray).controls;
  }

  showModal(type: string) {
    let service
    let data;
    let columns;
    switch (type) {
      case 'Registration numbers':
        service = this.registrationNumberService
        if (this.currentItemId) {
          service.fetchLatest(this.currentItemId).subscribe((res: any) => {
            data = res;
          });
        }
        service.getColumns().subscribe((res: any) => {
          columns = res;
        });
        break;
      case 'Responsible':
        service = this.responsibleService
        if (this.currentItemId) {
          service.fetchLatest(this.currentItemId).subscribe((res: any) => {
            data = res;
          });
        }
        service.getColumns().subscribe((res: any) => {
          columns = res;
        });
        break;
      case 'Drivers':
        service = this.driversService
        if (this.currentItemId) {
          service.fetchLatest(this.currentItemId).subscribe((res: any) => {
            data = res;
          });
        }
        service.getColumns().subscribe((res: any) => {
          columns = res;
        });
        break;
      case 'Fuel cards':
        service = this.fuelCardService
        if (this.currentItemId) {
          service.fetchLatest(this.currentItemId).subscribe((res: any) => {
            data = res;
          });
        }
        service.getColumns().subscribe((res: any) => {
          columns = res;
        });
        break;
    }
    const modal = this.modalService.create({
      nzTitle: type,
      nzContent: ReferenceListComponent,
      nzWidth: '45vw',
      nzStyle: {top: '0'},
      nzData: {
        carId: this.currentItemId,
        data: data,
        service: service,
        columns: columns,
        type:type
      },
      nzFooter: null,
    });
    modal.afterClose.subscribe((res) => {
      if (res) {
        this.message.info('Функцията не е имплементирана!');
      }
    });
  }

  public onSubmit() {
    if (this.currentItemId) {
      const sub1 = this.svc.updateRecords(this.currentItemId.id, this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: () => {}
      });
      this.subscriptions.push(sub1);
    } else {
      const sub2 = this.svc.createRecords( this.form.getRawValue()).subscribe({
        next: () => {
          this.modal.destroy();
        },
        error: () => {
        }
      });
      this.subscriptions.push(sub2);
    }
  }

  public addFuelOption() {
    (this.form.get('fuels') as FormArray).push(
      this.fb.group({
        type: this.fb.control(null,
          Validators.required),
        tankVolume: this.fb.control(null,
          Validators.required),
        distanceLimit: this.fb.control(null),
        moneyLimit: this.fb.control(null)
      })
    );
  }

  public removeFuelOption(index: any) {
    (this.form.get('fuels') as FormArray).removeAt(index);
  }

  private getOptions() {
    this.fuelOptions = [
      {value: Fuels.DIESEL, label: 'Diesel'},
      {value: Fuels.GASOLINE, label: 'Gasoline'},
      {value: Fuels.LPG, label: 'LPG'},
      {value: Fuels.METHANOL, label: 'Methanol'},
    ];

    this.vehicleOptions = [
      {value: VehicleType.CAR, label: 'Car'},
      {value: VehicleType.TRUCK, label: 'Truck'},

    ];

    this.categoryOptions = [
      {value: DrivingCategoryType.A, label: 'A'},
      {value: DrivingCategoryType.B, label: 'B'},
      {value: DrivingCategoryType.BE, label: 'BE'},
      {value: DrivingCategoryType.C, label: 'C'},
      {value: DrivingCategoryType.C1E, label: 'C1E'},
      {value: DrivingCategoryType.D, label: 'D'},
      {value: DrivingCategoryType.D1E, label: 'D1E'},
      {value: DrivingCategoryType.DE, label: 'DE'},
      {value: DrivingCategoryType.Ttm, label: 'Ttm'},
      {value: DrivingCategoryType.Tkt, label: 'Tkt'},
    ];

    this.ownerNameOptions = [
      {value: Owner.FIRSTOWNER, label: 'First Owner'},
      {value: Owner.SECONDOWNER, label: 'Second Owner'},
      {value: Owner.THIRDOWNER, label: 'Third Owner'},
      {value: Owner.FOURTHOWNER, label: 'Fourth Owner'},
    ];

    this.headquartersOptions = [
      {value: Headquarter.HEADQUARTER1, label: 'First Headquarter'},
      {value: Headquarter.HEADQUARTER2, label: 'Second Headquarter'},
      {value: Headquarter.HEADQUARTER3, label: 'Third Headquarter'},
      {value: Headquarter.HEADQUARTER4, label: 'Fourth Headquarter'},
      {value: Headquarter.HEADQUARTER5, label: 'Fifth Headquarter'},
    ];
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
