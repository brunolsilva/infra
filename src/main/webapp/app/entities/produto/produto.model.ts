import { BaseEntity } from './../../shared';

const enum SimNaoEnum {
    'SIM',
    'NAO'
}

export class Produto implements BaseEntity {
    constructor(
        public id?: string,
        public produto?: number,
        public liberaSublimite?: SimNaoEnum,
    ) {
    }
}
