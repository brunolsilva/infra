package com.bruno.infra.repository;

import com.bruno.infra.domain.ProdutoCobertura;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProdutoCobertura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoCoberturaRepository extends MongoRepository<ProdutoCobertura,String> {
    
}
