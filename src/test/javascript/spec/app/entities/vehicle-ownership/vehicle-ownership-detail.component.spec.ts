/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BeyondInfinityAppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehicleOwnershipDetailComponent } from '../../../../../../main/webapp/app/entities/vehicle-ownership/vehicle-ownership-detail.component';
import { VehicleOwnershipService } from '../../../../../../main/webapp/app/entities/vehicle-ownership/vehicle-ownership.service';
import { VehicleOwnership } from '../../../../../../main/webapp/app/entities/vehicle-ownership/vehicle-ownership.model';

describe('Component Tests', () => {

    describe('VehicleOwnership Management Detail Component', () => {
        let comp: VehicleOwnershipDetailComponent;
        let fixture: ComponentFixture<VehicleOwnershipDetailComponent>;
        let service: VehicleOwnershipService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BeyondInfinityAppTestModule],
                declarations: [VehicleOwnershipDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VehicleOwnershipService,
                    JhiEventManager
                ]
            }).overrideTemplate(VehicleOwnershipDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehicleOwnershipDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleOwnershipService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VehicleOwnership(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vehicleOwnership).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
