import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { VehicleOwnership } from './vehicle-ownership.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehicleOwnershipService {

    private resourceUrl = SERVER_API_URL + 'api/vehicle-ownerships';

    constructor(private http: Http) { }

    create(vehicleOwnership: VehicleOwnership): Observable<VehicleOwnership> {
        const copy = this.convert(vehicleOwnership);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(vehicleOwnership: VehicleOwnership): Observable<VehicleOwnership> {
        const copy = this.convert(vehicleOwnership);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<VehicleOwnership> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findBy(vehicleId: number, userId: number): Observable<VehicleOwnership> {
        return this.http.get(`${this.resourceUrl}?vehicleId.equals=${vehicleId}&ownerId.equals=${userId}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to VehicleOwnership.
     */
    private convertItemFromServer(json: any): VehicleOwnership {
        const entity: VehicleOwnership = Object.assign(new VehicleOwnership(), json);
        return entity;
    }

    /**
     * Convert a VehicleOwnership to a JSON which can be sent to the server.
     */
    private convert(vehicleOwnership: VehicleOwnership): VehicleOwnership {
        const copy: VehicleOwnership = Object.assign({}, vehicleOwnership);
        return copy;
    }
}
