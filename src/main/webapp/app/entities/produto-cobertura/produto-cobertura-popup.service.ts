import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProdutoCobertura } from './produto-cobertura.model';
import { ProdutoCoberturaService } from './produto-cobertura.service';

@Injectable()
export class ProdutoCoberturaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private produtoCoberturaService: ProdutoCoberturaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.produtoCoberturaService.find(id).subscribe((produtoCobertura) => {
                    if (produtoCobertura.dataInicioVigencia) {
                        produtoCobertura.dataInicioVigencia = {
                            year: produtoCobertura.dataInicioVigencia.getFullYear(),
                            month: produtoCobertura.dataInicioVigencia.getMonth() + 1,
                            day: produtoCobertura.dataInicioVigencia.getDate()
                        };
                    }
                    if (produtoCobertura.dataFimVigencia) {
                        produtoCobertura.dataFimVigencia = {
                            year: produtoCobertura.dataFimVigencia.getFullYear(),
                            month: produtoCobertura.dataFimVigencia.getMonth() + 1,
                            day: produtoCobertura.dataFimVigencia.getDate()
                        };
                    }
                    this.ngbModalRef = this.produtoCoberturaModalRef(component, produtoCobertura);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.produtoCoberturaModalRef(component, new ProdutoCobertura());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    produtoCoberturaModalRef(component: Component, produtoCobertura: ProdutoCobertura): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.produtoCobertura = produtoCobertura;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
