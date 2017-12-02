import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VehicleOwnershipComponent } from './vehicle-ownership.component';
import { VehicleOwnershipDetailComponent } from './vehicle-ownership-detail.component';
import { VehicleOwnershipPopupComponent } from './vehicle-ownership-dialog.component';
import { VehicleOwnershipDeletePopupComponent } from './vehicle-ownership-delete-dialog.component';

export const vehicleOwnershipRoute: Routes = [
    {
        path: 'vehicle-ownership',
        component: VehicleOwnershipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleOwnerships'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vehicle-ownership/:id',
        component: VehicleOwnershipDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleOwnerships'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vehicleOwnershipPopupRoute: Routes = [
    {
        path: 'vehicle-ownership-new',
        component: VehicleOwnershipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleOwnerships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehicle-ownership/:id/edit',
        component: VehicleOwnershipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleOwnerships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehicle-ownership/:id/delete',
        component: VehicleOwnershipDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VehicleOwnerships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
