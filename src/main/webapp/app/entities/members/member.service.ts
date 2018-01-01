import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {SERVER_API_URL} from '../../app.constants';
import {Member} from './member.model';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class MemberService {

    private resourceUrl = SERVER_API_URL + 'api/members';

    constructor(private http: Http) {
    }

    find(id: number): Observable<Member> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    /**
     * Convert a returned JSON object to Vehicle.
     */
    private convertItemFromServer(json: any): Member {
        const entity: Member = Object.assign(new Member(), json);
        return entity;
    }
}
