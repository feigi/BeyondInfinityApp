import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {SERVER_API_URL} from '../../app.constants';
import {Member} from './member.model';
import {Observable} from 'rxjs/Observable';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {createRequestOption} from '../../shared/model/request-util';

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

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    /**
     * Convert a returned JSON object to Vehicle.
     */
    private convertItemFromServer(json: any): Member {
        const entity: Member = Object.assign(new Member(), json);
        return entity;
    }
}
