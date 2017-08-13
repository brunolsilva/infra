package com.bruno.infra.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bruno.infra.domain.ProdutoCobertura;
import com.bruno.infra.service.ProdutoCoberturaService;
import com.bruno.infra.web.rest.util.HeaderUtil;
import com.bruno.infra.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProdutoCobertura.
 */
@RestController
@RequestMapping("/api")
public class ProdutoCoberturaResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoCoberturaResource.class);

    private static final String ENTITY_NAME = "produtoCobertura";

    private final ProdutoCoberturaService produtoCoberturaService;

    public ProdutoCoberturaResource(ProdutoCoberturaService produtoCoberturaService) {
        this.produtoCoberturaService = produtoCoberturaService;
    }

    /**
     * POST  /produto-coberturas : Create a new produtoCobertura.
     *
     * @param produtoCobertura the produtoCobertura to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produtoCobertura, or with status 400 (Bad Request) if the produtoCobertura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produto-coberturas")
    @Timed
    public ResponseEntity<ProdutoCobertura> createProdutoCobertura(@Valid @RequestBody ProdutoCobertura produtoCobertura) throws URISyntaxException {
        log.debug("REST request to save ProdutoCobertura : {}", produtoCobertura);
        if (produtoCobertura.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produtoCobertura cannot already have an ID")).body(null);
        }
        ProdutoCobertura result = produtoCoberturaService.save(produtoCobertura);
        return ResponseEntity.created(new URI("/api/produto-coberturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produto-coberturas : Updates an existing produtoCobertura.
     *
     * @param produtoCobertura the produtoCobertura to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produtoCobertura,
     * or with status 400 (Bad Request) if the produtoCobertura is not valid,
     * or with status 500 (Internal Server Error) if the produtoCobertura couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produto-coberturas")
    @Timed
    public ResponseEntity<ProdutoCobertura> updateProdutoCobertura(@Valid @RequestBody ProdutoCobertura produtoCobertura) throws URISyntaxException {
        log.debug("REST request to update ProdutoCobertura : {}", produtoCobertura);
        if (produtoCobertura.getId() == null) {
            return createProdutoCobertura(produtoCobertura);
        }
        ProdutoCobertura result = produtoCoberturaService.save(produtoCobertura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produtoCobertura.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produto-coberturas : get all the produtoCoberturas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produtoCoberturas in body
     */
    @GetMapping("/produto-coberturas")
    @Timed
    public ResponseEntity<List<ProdutoCobertura>> getAllProdutoCoberturas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProdutoCoberturas");
        Page<ProdutoCobertura> page = produtoCoberturaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produto-coberturas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produto-coberturas/:id : get the "id" produtoCobertura.
     *
     * @param id the id of the produtoCobertura to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produtoCobertura, or with status 404 (Not Found)
     */
    @GetMapping("/produto-coberturas/{id}")
    @Timed
    public ResponseEntity<ProdutoCobertura> getProdutoCobertura(@PathVariable String id) {
        log.debug("REST request to get ProdutoCobertura : {}", id);
        ProdutoCobertura produtoCobertura = produtoCoberturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produtoCobertura));
    }

    /**
     * DELETE  /produto-coberturas/:id : delete the "id" produtoCobertura.
     *
     * @param id the id of the produtoCobertura to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produto-coberturas/{id}")
    @Timed
    public ResponseEntity<Void> deleteProdutoCobertura(@PathVariable String id) {
        log.debug("REST request to delete ProdutoCobertura : {}", id);
        produtoCoberturaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
