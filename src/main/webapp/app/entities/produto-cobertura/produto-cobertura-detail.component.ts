import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ProdutoCobertura } from './produto-cobertura.model';
import { ProdutoCoberturaService } from './produto-cobertura.service';

@Component({
    selector: 'jhi-produto-cobertura-detail',
    templateUrl: './produto-cobertura-detail.component.html'
})
export class ProdutoCoberturaDetailComponent implements OnInit, OnDestroy {

    produtoCobertura: ProdutoCobertura;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private produtoCoberturaService: ProdutoCoberturaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProdutoCoberturas();
    }

    load(id) {
        this.produtoCoberturaService.find(id).subscribe((produtoCobertura) => {
            this.produtoCobertura = produtoCobertura;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProdutoCoberturas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'produtoCoberturaListModification',
            (response) => this.load(this.produtoCobertura.id)
        );
    }
}
