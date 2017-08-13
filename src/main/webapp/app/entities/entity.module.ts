import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InfraProdutoModule } from './produto/produto.module';
import { InfraProdutoCoberturaModule } from './produto-cobertura/produto-cobertura.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        InfraProdutoModule,
        InfraProdutoCoberturaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InfraEntityModule {}
