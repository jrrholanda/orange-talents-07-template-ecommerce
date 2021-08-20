package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @NotNull @Valid
    private Produto produto;
    @Positive
    private int quantidade;
    @ManyToOne @NotNull @Valid
    private Usuario comprador;
    @Enumerated @NotNull
    private GatewayPagamento gatewayPagamento;


    @Deprecated
    public Compra() {
    }

    public Compra(@NotNull @Valid Produto produto, @Positive int quantidade,
                  @NotNull @Valid Usuario comprador, GatewayPagamento gatewayPagamento) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.gatewayPagamento = gatewayPagamento;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public Usuario getDonoProduto(){ return produto.getDono();}

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }

    public String geraUrlGateway(UriComponentsBuilder uriComponentsBuilder){
        return this.gatewayPagamento.criaUrl(this, uriComponentsBuilder);
    }
}
