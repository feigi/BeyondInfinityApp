import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { BeyondInfinityAppSharedModule, UserRouteAccessService } from './shared';
import { BeyondInfinityAppAppRoutingModule} from './app-routing.module';
import { BeyondInfinityAppHomeModule } from './home/home.module';
import { BeyondInfinityAppAdminModule } from './admin/admin.module';
import { BeyondInfinityAppAccountModule } from './account/account.module';
import { BeyondInfinityAppEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        BeyondInfinityAppAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        BeyondInfinityAppSharedModule,
        BeyondInfinityAppHomeModule,
        BeyondInfinityAppAdminModule,
        BeyondInfinityAppAccountModule,
        BeyondInfinityAppEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BeyondInfinityAppAppModule {}
