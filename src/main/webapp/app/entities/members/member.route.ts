import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MemberComponent} from './member.component';
import {MemberDetailComponent} from './member-detail.component';
import {VehiclePopupComponent} from '../vehicle/vehicle-dialog.component';
import {VehicleDeletePopupComponent} from '../vehicle/vehicle-delete-dialog.component';
import {UnfleetPopupComponent} from './unfleet-dialog.component';

export const memberRoute: Routes = [
    {
        path: 'member',
        component: MemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehicles'
        },
    }, {
        path: 'member/:id',
        component: MemberDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehicles'
        },
    }
];

export const memberPopupRoute: Routes = [
    {
        path: 'member/:userId/unfleet/:vehicleId',
        component: UnfleetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehicles'
        },
        outlet: 'popup'
    }
];
