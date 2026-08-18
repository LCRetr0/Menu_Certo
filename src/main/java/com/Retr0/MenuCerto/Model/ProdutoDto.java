package com.Retr0.MenuCerto.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdutoDto(
        String code,
        @JsonAlias("product_name") String nome,
        String brands,
        @JsonAlias("nutriscore_grade") String nutriScore
) {
    @Override
    public String toString() {
        return String.format(
                "========================================\n" +
                        "Código de Barras: %s\n" +
                        "Produto          : %s\n" +
                        "Marca            : %s\n" +
                        "Nutri-Score      : %s\n" +
                        "========================================",
                code != null ? code : "N/A",
                nome != null ? nome : "Nome não informado",
                brands != null ? brands : "Marca não informada",
                nutriScore != null ? nutriScore.toUpperCase() : "N/A"
        );
    }
}