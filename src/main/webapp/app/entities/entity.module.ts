import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BeyondInfinityAppVehicleModule } from './vehicle/vehicle.module';
import { BeyondInfinityAppVehicleOwnershipModule } from './vehicle-ownership/vehicle-ownership.module';
import {BeyondInfinityAppMemberModule} from './members/member.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BeyondInfinityAppVehicleModule,
        BeyondInfinityAppVehicleOwnershipModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        BeyondInfinityAppMemberModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BeyondInfinityAppEntityModule {}
