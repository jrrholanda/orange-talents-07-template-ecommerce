package br.com.zup.mercadolivre.produto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCaracteristicaRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaCaracteristicaRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public CaracteristicaProduto toModel(@NotNull @Valid Produto produto) {
        return new CaracteristicaProduto(nome,descricao, produto);
    }
}
