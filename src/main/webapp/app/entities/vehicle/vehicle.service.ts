import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Vehicle } from './vehicle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VehicleService {

    private resourceUrl = SERVER_API_URL + 'api/vehicles';

    constructor(private http: Http) { }

    create(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(vehicle: Vehicle): Observable<Vehicle> {
        const copy = this.convert(vehicle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    updateMyVehicles(vehicles: Vehicle[]): Observable<Vehicle[]> {
        return this.http.put(this.resourceUrl + '/mine', vehicles).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Vehicle> {
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
     * Convert a returned JSON object to Vehicle.
     */
    private convertItemFromServer(json: any): Vehicle {
        const entity: Vehicle = Object.assign(new Vehicle(), json);
        return entity;
    }

    /**
     * Convert a Vehicle to a JSON which can be sent to the server.
     */
    private convert(vehicle: Vehicle): Vehicle {
        const copy: Vehicle = Object.assign({}, vehicle);
        return copy;
    }
}
