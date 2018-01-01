import { BaseEntity } from './../../shared';

export const enum Manufacturer {
    'AEGIS_DYNAMICS',
    'ANVIL_AEROSPACE',
    'ARGO',
    'BANU',
    'CONSOLIDATED_OUTLAND',
    'CRUSADER_INDUSTRIES',
    'DRAKE_INERPLANETARY',
    'ESPERIA',
    'KRUGER_INTERGALACTIC',
    'MISC',
    'ORIGIN_JUMPWORKS',
    'RSI',
    'TUMBRIL',
    'VANDUUL'
}

export class Vehicle implements BaseEntity {
    constructor(
        public id?: number,
        public manufacturer?: Manufacturer,
        public model?: string,
        public url?: string,
        public ownedByUser?: boolean
    ) {
    }
}
