package com.bruno.infra.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProdutoCobertura.
 */
@Document(collection = "produto_cobertura")
public class ProdutoCobertura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Max(value = 9999)
    @Field("cobertura")
    private Integer cobertura;

    @NotNull
    @Max(value = 9999)
    @Field("produto")
    private Integer produto;

    @NotNull
    @Field("data_inicio_vigencia")
    private LocalDate dataInicioVigencia;

    @Field("data_fim_vigencia")
    private LocalDate dataFimVigencia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCobertura() {
        return cobertura;
    }

    public ProdutoCobertura cobertura(Integer cobertura) {
        this.cobertura = cobertura;
        return this;
    }

    public void setCobertura(Integer cobertura) {
        this.cobertura = cobertura;
    }

    public Integer getProduto() {
        return produto;
    }

    public ProdutoCobertura produto(Integer produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public LocalDate getDataInicioVigencia() {
        return dataInicioVigencia;
    }

    public ProdutoCobertura dataInicioVigencia(LocalDate dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
        return this;
    }

    public void setDataInicioVigencia(LocalDate dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public LocalDate getDataFimVigencia() {
        return dataFimVigencia;
    }

    public ProdutoCobertura dataFimVigencia(LocalDate dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
        return this;
    }

    public void setDataFimVigencia(LocalDate dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProdutoCobertura produtoCobertura = (ProdutoCobertura) o;
        if (produtoCobertura.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoCobertura.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoCobertura{" +
            "id=" + getId() +
            ", cobertura='" + getCobertura() + "'" +
            ", produto='" + getProduto() + "'" +
            ", dataInicioVigencia='" + getDataInicioVigencia() + "'" +
            ", dataFimVigencia='" + getDataFimVigencia() + "'" +
            "}";
    }
}
