package com.bruno.infra.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.bruno.infra.domain.enumeration.SimNaoEnum;

/**
 * A Produto.
 */
@Document(collection = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Max(value = 9999)
    @Field("produto")
    private Integer produto;

    @NotNull
    @Field("libera_sublimite")
    private SimNaoEnum liberaSublimite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getProduto() {
        return produto;
    }

    public Produto produto(Integer produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public SimNaoEnum getLiberaSublimite() {
        return liberaSublimite;
    }

    public Produto liberaSublimite(SimNaoEnum liberaSublimite) {
        this.liberaSublimite = liberaSublimite;
        return this;
    }

    public void setLiberaSublimite(SimNaoEnum liberaSublimite) {
        this.liberaSublimite = liberaSublimite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produto produto = (Produto) o;
        if (produto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", produto='" + getProduto() + "'" +
            ", liberaSublimite='" + getLiberaSublimite() + "'" +
            "}";
    }
}
