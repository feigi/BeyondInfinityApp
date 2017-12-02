import { BaseEntity } from './../../shared';

export class VehicleOwnership implements BaseEntity {
    constructor(
        public id?: number,
        public vehicleId?: number,
        public ownerId?: number,
    ) {
    }
}
