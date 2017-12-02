import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VehicleOwnership } from './vehicle-ownership.model';
import { VehicleOwnershipPopupService } from './vehicle-ownership-popup.service';
import { VehicleOwnershipService } from './vehicle-ownership.service';

@Component({
    selector: 'jhi-vehicle-ownership-delete-dialog',
    templateUrl: './vehicle-ownership-delete-dialog.component.html'
})
export class VehicleOwnershipDeleteDialogComponent {

    vehicleOwnership: VehicleOwnership;

    constructor(
        private vehicleOwnershipService: VehicleOwnershipService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vehicleOwnershipService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vehicleOwnershipListModification',
                content: 'Deleted an vehicleOwnership'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vehicle-ownership-delete-popup',
    template: ''
})
export class VehicleOwnershipDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehicleOwnershipPopupService: VehicleOwnershipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vehicleOwnershipPopupService
                .open(VehicleOwnershipDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
