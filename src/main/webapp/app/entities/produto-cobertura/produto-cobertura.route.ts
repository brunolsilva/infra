import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProdutoCoberturaComponent } from './produto-cobertura.component';
import { ProdutoCoberturaDetailComponent } from './produto-cobertura-detail.component';
import { ProdutoCoberturaPopupComponent } from './produto-cobertura-dialog.component';
import { ProdutoCoberturaDeletePopupComponent } from './produto-cobertura-delete-dialog.component';

@Injectable()
export class ProdutoCoberturaResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const produtoCoberturaRoute: Routes = [
    {
        path: 'produto-cobertura',
        component: ProdutoCoberturaComponent,
        resolve: {
            'pagingParams': ProdutoCoberturaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infraApp.produtoCobertura.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'produto-cobertura/:id',
        component: ProdutoCoberturaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infraApp.produtoCobertura.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const produtoCoberturaPopupRoute: Routes = [
    {
        path: 'produto-cobertura-new',
        component: ProdutoCoberturaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infraApp.produtoCobertura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produto-cobertura/:id/edit',
        component: ProdutoCoberturaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infraApp.produtoCobertura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produto-cobertura/:id/delete',
        component: ProdutoCoberturaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infraApp.produtoCobertura.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
