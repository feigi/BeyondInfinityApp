import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MemberComponent} from './member.component';
import {MemberDetailComponent} from './member-detail.component';

export const memberRoute: Routes = [
    {
        path: 'member',
        component: MemberComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehicles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'member/:id',
        component: MemberDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vehicles'
        },
        canActivate: [UserRouteAccessService]
    }
];
