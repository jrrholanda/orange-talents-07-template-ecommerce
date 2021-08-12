package br.com.zup.mercadolivre.produto;

import java.math.BigDecimal;

public class ProdutoResponse {

    private String nome;
    private BigDecimal valor;

    public ProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
