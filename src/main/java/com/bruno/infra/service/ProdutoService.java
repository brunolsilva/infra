package com.bruno.infra.service;

import com.bruno.infra.domain.Produto;
import com.bruno.infra.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Produto.
 */
@Service
public class ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Save a produto.
     *
     * @param produto the entity to save
     * @return the persisted entity
     */
    public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        return produtoRepository.save(produto);
    }

    /**
     *  Get all the produtos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable);
    }

    /**
     *  Get one produto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Produto findOne(String id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findOne(id);
    }

    /**
     *  Delete the  produto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.delete(id);
    }
}
