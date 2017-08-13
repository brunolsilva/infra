import { BaseEntity } from './../../shared';

export class ProdutoCobertura implements BaseEntity {
    constructor(
        public id?: string,
        public cobertura?: number,
        public produto?: number,
        public dataInicioVigencia?: any,
        public dataFimVigencia?: any,
    ) {
    }
}
