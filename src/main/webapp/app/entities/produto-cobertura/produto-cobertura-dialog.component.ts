import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProdutoCobertura } from './produto-cobertura.model';
import { ProdutoCoberturaPopupService } from './produto-cobertura-popup.service';
import { ProdutoCoberturaService } from './produto-cobertura.service';

@Component({
    selector: 'jhi-produto-cobertura-dialog',
    templateUrl: './produto-cobertura-dialog.component.html'
})
export class ProdutoCoberturaDialogComponent implements OnInit {

    produtoCobertura: ProdutoCobertura;
    isSaving: boolean;
    dataInicioVigenciaDp: any;
    dataFimVigenciaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private produtoCoberturaService: ProdutoCoberturaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.produtoCobertura.id !== undefined) {
            this.subscribeToSaveResponse(
                this.produtoCoberturaService.update(this.produtoCobertura));
        } else {
            this.subscribeToSaveResponse(
                this.produtoCoberturaService.create(this.produtoCobertura));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProdutoCobertura>) {
        result.subscribe((res: ProdutoCobertura) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProdutoCobertura) {
        this.eventManager.broadcast({ name: 'produtoCoberturaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-produto-cobertura-popup',
    template: ''
})
export class ProdutoCoberturaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private produtoCoberturaPopupService: ProdutoCoberturaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.produtoCoberturaPopupService
                    .open(ProdutoCoberturaDialogComponent as Component, params['id']);
            } else {
                this.produtoCoberturaPopupService
                    .open(ProdutoCoberturaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
