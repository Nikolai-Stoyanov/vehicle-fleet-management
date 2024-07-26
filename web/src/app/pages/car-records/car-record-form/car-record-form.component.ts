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
import {CarModelsService} from "../../nomenclatures/car-models/car-models.service";
import {CarBrandsService} from "../../nomenclatures/car-brands/car-brands.service";

@Component({
  selector: 'vfm-car-record-form',
  templateUrl: './car-record-form.component.html',
  styleUrls: ['./car-record-form.component.scss'],
})
export class CarRecordFormComponent implements OnInit, OnDestroy {
  public form!: FormGroup;
  @Input() public currentItemId: any;
  public currentItem: any;
  public fuelOptions: any;
  public categoryOptions: any[] = [];
  public headquartersOptions: any[] = [];
  public ownerNameOptions: any[] = [];
  public vehicleOptions: any[] = [];
  public carBrandOptions: any[] = [];
  public carModelOptions: any[] = [];
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
    private carModelService:CarModelsService,
    private carBrandService:CarBrandsService
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
        this.currentItem?.loadCapacity || null
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
        {value:this.currentItem?.createdBy || null,disabled:true}
      ),
      createdDate: this.fb.control(
        {value:this.currentItem?.createdDate || null,disabled:true}
      ),
      modifyBy: this.fb.control(
        {value:this.currentItem?.modifyBy || null,disabled:true}
      ),
      modifyDate: this.fb.control(
        {value:this.currentItem?.modifyDate || null,disabled:true}
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
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return
    }
    console.log(this.form.getRawValue());
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
      {value:{id:1,name:Fuels.DIESEL} , label: Fuels.DIESEL},
      {value:{id:2,name:Fuels.GASOLINE}, label: Fuels.GASOLINE},
      {value:{id:3,name: Fuels.LPG}, label: Fuels.LPG},
      {value:{id:4,name: Fuels.METHANOL}, label: Fuels.METHANOL},
    ];

    this.vehicleOptions = [
      {value: {id:1,name:VehicleType.CAR}, label: VehicleType.CAR},
      {value: {id:2,name:VehicleType.TRUCK}, label: VehicleType.TRUCK},
    ];

    this.categoryOptions = [
      {value:{id:1,name: DrivingCategoryType.A}, label: DrivingCategoryType.A},
      {value:{id:2,name: DrivingCategoryType.B}, label: DrivingCategoryType.B},
      {value:{id:3,name: DrivingCategoryType.BE}, label: DrivingCategoryType.BE},
      {value:{id:4,name: DrivingCategoryType.C}, label: DrivingCategoryType.C},
      {value:{id:5,name: DrivingCategoryType.C1E}, label: DrivingCategoryType.C1E},
      {value:{id:6,name: DrivingCategoryType.D}, label: DrivingCategoryType.D},
      {value:{id:7,name: DrivingCategoryType.D1E}, label: DrivingCategoryType.D1E},
      {value:{id:8,name: DrivingCategoryType.DE}, label: DrivingCategoryType.DE},
      {value:{id:9,name: DrivingCategoryType.Ttm}, label: DrivingCategoryType.Ttm},
      {value:{id:10,name: DrivingCategoryType.Tkt}, label: DrivingCategoryType.Tkt},
    ];

    this.ownerNameOptions = [
      {value:{id:1,name:  Owner.FIRSTOWNER}, label: Owner.FIRSTOWNER},
      {value:{id:1,name:  Owner.SECONDOWNER}, label:Owner.SECONDOWNER},
      {value:{id:1,name:  Owner.THIRDOWNER}, label: Owner.THIRDOWNER},
      {value:{id:1,name:  Owner.FOURTHOWNER}, label:Owner.FOURTHOWNER}
    ];

    this.headquartersOptions = [
      {value:{id:1,name:  Headquarter.HEADQUARTER1}, label: Headquarter.HEADQUARTER1},
      {value:{id:1,name:  Headquarter.HEADQUARTER2}, label: Headquarter.HEADQUARTER2},
      {value:{id:1,name:  Headquarter.HEADQUARTER3}, label: Headquarter.HEADQUARTER3},
      {value:{id:1,name:  Headquarter.HEADQUARTER4}, label: Headquarter.HEADQUARTER4},
      {value:{id:1,name:  Headquarter.HEADQUARTER5}, label: Headquarter.HEADQUARTER5},
    ];

    this.carModelService.fetchLatest().subscribe((res)=>{
      res.forEach((item:any)=>{
        this.carModelOptions.push({value:item, label: item.name});
      })

    })
    this.carBrandService.fetchLatest().subscribe((res)=>{
      res.forEach((item:any)=>{
        this.carBrandOptions.push({value:item, label: item.name});
      })
    })
  }

  ngOnDestroy() {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
  }
}
