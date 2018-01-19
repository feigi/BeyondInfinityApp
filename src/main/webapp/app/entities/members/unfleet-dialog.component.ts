import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {VehicleOwnership, VehicleOwnershipService} from '../vehicle-ownership';
import {UnfleetPopupService} from './unfleet-popup.service';
import {JhiEventManager} from 'ng-jhipster';

@Component({
    selector: 'jhi-unfleet-dialog',
    templateUrl: './unfleet-dialog.component.html'
})
export class UnfleetDialogComponent {

    vehicleOwnership: VehicleOwnership;

    constructor(private vehicleOwnershipService: VehicleOwnershipService, private eventManager: JhiEventManager,
                public activeModal: NgbActiveModal) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete() {
        this.vehicleOwnershipService.delete(this.vehicleOwnership.id)
            .subscribe(() => this.onSaveSuccess());
    }

    private onSaveSuccess() {
        this.eventManager.broadcast({name: 'vehicleOwnershipListModification', content: 'OK'});
        this.activeModal.dismiss(true);
    }
}

@Component({
    selector: 'jhi-unfleet-popup',
    template: ''
})
export class UnfleetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute, private unfleetPopupService: UnfleetPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.unfleetPopupService.open(UnfleetDialogComponent as Component, params['vehicleId'], params['userId']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
