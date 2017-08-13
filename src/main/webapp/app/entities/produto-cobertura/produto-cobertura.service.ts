import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ProdutoCobertura } from './produto-cobertura.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProdutoCoberturaService {

    private resourceUrl = 'api/produto-coberturas';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(produtoCobertura: ProdutoCobertura): Observable<ProdutoCobertura> {
        const copy = this.convert(produtoCobertura);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(produtoCobertura: ProdutoCobertura): Observable<ProdutoCobertura> {
        const copy = this.convert(produtoCobertura);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: string): Observable<ProdutoCobertura> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dataInicioVigencia = this.dateUtils
            .convertLocalDateFromServer(entity.dataInicioVigencia);
        entity.dataFimVigencia = this.dateUtils
            .convertLocalDateFromServer(entity.dataFimVigencia);
    }

    private convert(produtoCobertura: ProdutoCobertura): ProdutoCobertura {
        const copy: ProdutoCobertura = Object.assign({}, produtoCobertura);
        copy.dataInicioVigencia = this.dateUtils
            .convertLocalDateToServer(produtoCobertura.dataInicioVigencia);
        copy.dataFimVigencia = this.dateUtils
            .convertLocalDateToServer(produtoCobertura.dataFimVigencia);
        return copy;
    }
}
