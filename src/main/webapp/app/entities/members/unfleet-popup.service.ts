import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {VehicleOwnership, VehicleOwnershipService} from '../vehicle-ownership';

@Injectable()
export class UnfleetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal,
                private router: Router,
                private vehicleOwnershipService: VehicleOwnershipService) {
        this.ngbModalRef = null;
    }

    open(component: Component, vehicleId: number, ownerId: number): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (vehicleId && ownerId) {
                this.vehicleOwnershipService.findBy(vehicleId, ownerId).subscribe((vehicles) => {
                    this.ngbModalRef = this.vehicleModalRef(component, vehicles[0]);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    vehicleModalRef(component: Component, vehicleOwnership: VehicleOwnership): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehicleOwnership = vehicleOwnership;
        modalRef.result.then((result) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
