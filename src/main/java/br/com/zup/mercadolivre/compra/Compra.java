package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Pagamento> tentativasPagamento = new HashSet<>();


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

//    public void adicionaPagamento(@Valid GatewayPagamentoRequest request){
//        Pagamento tentativaPagamento = request.toPagamento(this);
//
//        Assert.state(!this.tentativasPagamento.contains(tentativaPagamento), "Já existe um pagamento semelhante em processamento");
//        Assert.state(!verificaStatusSucesso(), "Compra já concluída");
//
//        tentativasPagamento.add(tentativaPagamento);
//    }
//
//    public boolean verificaStatusSucesso(){
//        return tentativasPagamento.stream().anyMatch(Pagamento::concluidoComSucesso);
//    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", comprador=" + comprador +
                ", gatewayPagamento=" + gatewayPagamento +
                ", tentativasPagamento=" + tentativasPagamento +
                '}';
    }


    public void adicionaPagamento(@Valid GatewayPagamentoRequest request) {
        Pagamento novaTransacao = request.toPagamento(this);

        Assert.state(!this.tentativasPagamento.contains(novaTransacao),
                "Já existe um pagamento semelhante em processamento" + novaTransacao);
        Assert.state(transacoesConcluidasComSucesso().isEmpty(),"Compra já concluída");

        this.tentativasPagamento.add(novaTransacao);
    }

    private Set<Pagamento> transacoesConcluidasComSucesso() {
        Set<Pagamento> transacoesConcluidasComSucesso = this.tentativasPagamento.stream()
                .filter(Pagamento::concluidoComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1,"Já existe pagamentos concluidos com sucesso nesta compra "+this.id);

        return transacoesConcluidasComSucesso;
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();
    }
}
