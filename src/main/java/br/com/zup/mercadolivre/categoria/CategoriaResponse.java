package br.com.zup.mercadolivre.categoria;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CategoriaResponse {
    private String nome;

    public CategoriaResponse(Categoria categoria) {
        this.nome = categoria.getNome();
    }

    public String getNome() {
        return nome;
    }
}
