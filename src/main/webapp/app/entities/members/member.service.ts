import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {SERVER_API_URL} from '../../app.constants';

@Injectable()
export class MemberService {

    private resourceUrl = SERVER_API_URL + 'api/member';

    constructor(private http: Http) {
    }

}
