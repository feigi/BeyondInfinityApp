import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VehicleOwnership } from './vehicle-ownership.model';
import { VehicleOwnershipPopupService } from './vehicle-ownership-popup.service';
import { VehicleOwnershipService } from './vehicle-ownership.service';
import { Vehicle, VehicleService } from '../vehicle';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vehicle-ownership-dialog',
    templateUrl: './vehicle-ownership-dialog.component.html'
})
export class VehicleOwnershipDialogComponent implements OnInit {

    vehicleOwnership: VehicleOwnership;
    isSaving: boolean;

    vehicles: Vehicle[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private vehicleOwnershipService: VehicleOwnershipService,
        private vehicleService: VehicleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.vehicleService.query()
            .subscribe((res: ResponseWrapper) => { this.vehicles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehicleOwnership.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vehicleOwnershipService.update(this.vehicleOwnership));
        } else {
            this.subscribeToSaveResponse(
                this.vehicleOwnershipService.create(this.vehicleOwnership));
        }
    }

    private subscribeToSaveResponse(result: Observable<VehicleOwnership>) {
        result.subscribe((res: VehicleOwnership) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VehicleOwnership) {
        this.eventManager.broadcast({ name: 'vehicleOwnershipListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVehicleById(index: number, item: Vehicle) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vehicle-ownership-popup',
    template: ''
})
export class VehicleOwnershipPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehicleOwnershipPopupService: VehicleOwnershipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vehicleOwnershipPopupService
                    .open(VehicleOwnershipDialogComponent as Component, params['id']);
            } else {
                this.vehicleOwnershipPopupService
                    .open(VehicleOwnershipDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
