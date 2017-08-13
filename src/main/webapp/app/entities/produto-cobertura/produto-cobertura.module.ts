import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InfraSharedModule } from '../../shared';
import {
    ProdutoCoberturaService,
    ProdutoCoberturaPopupService,
    ProdutoCoberturaComponent,
    ProdutoCoberturaDetailComponent,
    ProdutoCoberturaDialogComponent,
    ProdutoCoberturaPopupComponent,
    ProdutoCoberturaDeletePopupComponent,
    ProdutoCoberturaDeleteDialogComponent,
    produtoCoberturaRoute,
    produtoCoberturaPopupRoute,
    ProdutoCoberturaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...produtoCoberturaRoute,
    ...produtoCoberturaPopupRoute,
];

@NgModule({
    imports: [
        InfraSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProdutoCoberturaComponent,
        ProdutoCoberturaDetailComponent,
        ProdutoCoberturaDialogComponent,
        ProdutoCoberturaDeleteDialogComponent,
        ProdutoCoberturaPopupComponent,
        ProdutoCoberturaDeletePopupComponent,
    ],
    entryComponents: [
        ProdutoCoberturaComponent,
        ProdutoCoberturaDialogComponent,
        ProdutoCoberturaPopupComponent,
        ProdutoCoberturaDeleteDialogComponent,
        ProdutoCoberturaDeletePopupComponent,
    ],
    providers: [
        ProdutoCoberturaService,
        ProdutoCoberturaPopupService,
        ProdutoCoberturaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InfraProdutoCoberturaModule {}
