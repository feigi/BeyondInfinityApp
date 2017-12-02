import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VehicleOwnership } from './vehicle-ownership.model';
import { VehicleOwnershipService } from './vehicle-ownership.service';

@Component({
    selector: 'jhi-vehicle-ownership-detail',
    templateUrl: './vehicle-ownership-detail.component.html'
})
export class VehicleOwnershipDetailComponent implements OnInit, OnDestroy {

    vehicleOwnership: VehicleOwnership;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vehicleOwnershipService: VehicleOwnershipService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVehicleOwnerships();
    }

    load(id) {
        this.vehicleOwnershipService.find(id).subscribe((vehicleOwnership) => {
            this.vehicleOwnership = vehicleOwnership;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVehicleOwnerships() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vehicleOwnershipListModification',
            (response) => this.load(this.vehicleOwnership.id)
        );
    }
}
