/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InfraTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProdutoCoberturaDetailComponent } from '../../../../../../main/webapp/app/entities/produto-cobertura/produto-cobertura-detail.component';
import { ProdutoCoberturaService } from '../../../../../../main/webapp/app/entities/produto-cobertura/produto-cobertura.service';
import { ProdutoCobertura } from '../../../../../../main/webapp/app/entities/produto-cobertura/produto-cobertura.model';

describe('Component Tests', () => {

    describe('ProdutoCobertura Management Detail Component', () => {
        let comp: ProdutoCoberturaDetailComponent;
        let fixture: ComponentFixture<ProdutoCoberturaDetailComponent>;
        let service: ProdutoCoberturaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InfraTestModule],
                declarations: [ProdutoCoberturaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProdutoCoberturaService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProdutoCoberturaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProdutoCoberturaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProdutoCoberturaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProdutoCobertura('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.produtoCobertura).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
