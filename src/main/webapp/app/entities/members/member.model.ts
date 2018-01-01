import { BaseEntity } from './../../shared';
import {Vehicle} from '../vehicle';

export class Member implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public vehicles?: Vehicle[]
    ) {
    }
}
