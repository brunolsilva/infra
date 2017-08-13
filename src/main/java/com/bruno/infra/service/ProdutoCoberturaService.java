package com.bruno.infra.service;

import com.bruno.infra.domain.ProdutoCobertura;
import com.bruno.infra.repository.ProdutoCoberturaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing ProdutoCobertura.
 */
@Service
public class ProdutoCoberturaService {

    private final Logger log = LoggerFactory.getLogger(ProdutoCoberturaService.class);

    private final ProdutoCoberturaRepository produtoCoberturaRepository;

    public ProdutoCoberturaService(ProdutoCoberturaRepository produtoCoberturaRepository) {
        this.produtoCoberturaRepository = produtoCoberturaRepository;
    }

    /**
     * Save a produtoCobertura.
     *
     * @param produtoCobertura the entity to save
     * @return the persisted entity
     */
    public ProdutoCobertura save(ProdutoCobertura produtoCobertura) {
        log.debug("Request to save ProdutoCobertura : {}", produtoCobertura);
        return produtoCoberturaRepository.save(produtoCobertura);
    }

    /**
     *  Get all the produtoCoberturas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ProdutoCobertura> findAll(Pageable pageable) {
        log.debug("Request to get all ProdutoCoberturas");
        return produtoCoberturaRepository.findAll(pageable);
    }

    /**
     *  Get one produtoCobertura by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ProdutoCobertura findOne(String id) {
        log.debug("Request to get ProdutoCobertura : {}", id);
        return produtoCoberturaRepository.findOne(id);
    }

    /**
     *  Delete the  produtoCobertura by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ProdutoCobertura : {}", id);
        produtoCoberturaRepository.delete(id);
    }
}
