import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeyondInfinityAppSharedModule } from '../../shared';
import {
    MemberService,
    MemberComponent,
    MemberDetailComponent,
    memberRoute,
} from './';
import {UnfleetDialogComponent, UnfleetPopupComponent} from './unfleet-dialog.component';
import {UnfleetPopupService} from './unfleet-popup.service';
import {memberPopupRoute} from './member.route';

const ENTITY_STATES = [
    ...memberRoute,
    ...memberPopupRoute
];

@NgModule({
    imports: [
        BeyondInfinityAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MemberComponent,
        MemberDetailComponent,
        UnfleetDialogComponent,
        UnfleetPopupComponent
    ],
    entryComponents: [
        MemberComponent,
        UnfleetDialogComponent
    ],
    providers: [
        MemberService,
        UnfleetPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BeyondInfinityAppMemberModule {}
