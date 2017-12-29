import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeyondInfinityAppSharedModule } from '../../shared';
import {
    MemberService,
    MemberComponent,
    MemberDetailComponent,
    memberRoute,
} from './';

const ENTITY_STATES = [
    ...memberRoute,
];

@NgModule({
    imports: [
        BeyondInfinityAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MemberComponent,
        MemberDetailComponent,
    ],
    entryComponents: [
        MemberComponent
    ],
    providers: [
        MemberService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BeyondInfinityAppMemberModule {}
