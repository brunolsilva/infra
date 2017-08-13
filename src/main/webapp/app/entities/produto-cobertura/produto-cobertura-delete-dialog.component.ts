import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProdutoCobertura } from './produto-cobertura.model';
import { ProdutoCoberturaPopupService } from './produto-cobertura-popup.service';
import { ProdutoCoberturaService } from './produto-cobertura.service';

@Component({
    selector: 'jhi-produto-cobertura-delete-dialog',
    templateUrl: './produto-cobertura-delete-dialog.component.html'
})
export class ProdutoCoberturaDeleteDialogComponent {

    produtoCobertura: ProdutoCobertura;

    constructor(
        private produtoCoberturaService: ProdutoCoberturaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.produtoCoberturaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'produtoCoberturaListModification',
                content: 'Deleted an produtoCobertura'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-produto-cobertura-delete-popup',
    template: ''
})
export class ProdutoCoberturaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private produtoCoberturaPopupService: ProdutoCoberturaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.produtoCoberturaPopupService
                .open(ProdutoCoberturaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
