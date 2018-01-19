import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {Member} from './member.model';
import {MemberService} from './member.service';

@Component({
    selector: 'jhi-member-detail',
    templateUrl: './member-detail.component.html'
})
export class MemberDetailComponent implements OnInit, OnDestroy {

    member: Member;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager,
                private memberService: MemberService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMembers();
        this.registerChangeInVehicleOwnerships();
    }

    load(id) {
        this.memberService.find(id).subscribe((member) => {
            this.member = member;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMembers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'memberListModification',
            () => this.load(this.member.id)
        );
    }

    private registerChangeInVehicleOwnerships() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vehicleOwnershipListModification',
            () => this.load(this.member.id)
        );
    }
}
