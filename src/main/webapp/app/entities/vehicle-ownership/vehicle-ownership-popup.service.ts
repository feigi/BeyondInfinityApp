import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VehicleOwnership } from './vehicle-ownership.model';
import { VehicleOwnershipService } from './vehicle-ownership.service';

@Injectable()
export class VehicleOwnershipPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vehicleOwnershipService: VehicleOwnershipService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.vehicleOwnershipService.find(id).subscribe((vehicleOwnership) => {
                    this.ngbModalRef = this.vehicleOwnershipModalRef(component, vehicleOwnership);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vehicleOwnershipModalRef(component, new VehicleOwnership());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vehicleOwnershipModalRef(component: Component, vehicleOwnership: VehicleOwnership): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehicleOwnership = vehicleOwnership;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
