import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeyondInfinityAppSharedModule } from '../../shared';
import { BeyondInfinityAppAdminModule } from '../../admin/admin.module';
import {
    VehicleOwnershipService,
    VehicleOwnershipPopupService,
    VehicleOwnershipComponent,
    VehicleOwnershipDetailComponent,
    VehicleOwnershipDialogComponent,
    VehicleOwnershipPopupComponent,
    VehicleOwnershipDeletePopupComponent,
    VehicleOwnershipDeleteDialogComponent,
    vehicleOwnershipRoute,
    vehicleOwnershipPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vehicleOwnershipRoute,
    ...vehicleOwnershipPopupRoute,
];

@NgModule({
    imports: [
        BeyondInfinityAppSharedModule,
        BeyondInfinityAppAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VehicleOwnershipComponent,
        VehicleOwnershipDetailComponent,
        VehicleOwnershipDialogComponent,
        VehicleOwnershipDeleteDialogComponent,
        VehicleOwnershipPopupComponent,
        VehicleOwnershipDeletePopupComponent,
    ],
    entryComponents: [
        VehicleOwnershipComponent,
        VehicleOwnershipDialogComponent,
        VehicleOwnershipPopupComponent,
        VehicleOwnershipDeleteDialogComponent,
        VehicleOwnershipDeletePopupComponent,
    ],
    providers: [
        VehicleOwnershipService,
        VehicleOwnershipPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BeyondInfinityAppVehicleOwnershipModule {}
