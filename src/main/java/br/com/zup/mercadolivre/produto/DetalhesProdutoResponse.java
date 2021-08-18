package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.opiniao.OpiniaoResponse;
import br.com.zup.mercadolivre.pergunta.PerguntaResponse;

import java.math.BigDecimal;
import java.util.*;

public class DetalhesProdutoResponse {

    private String nomeProduto;
    private BigDecimal precoProduto;
    private String descricaoProduto;
    private Set<OpiniaoResponse> opinioesProduto = new HashSet<>();
    private double  mediaNotaProduto;
    private int totalNotasProduto;
    private SortedSet<PerguntaResponse> perguntasProduto = new TreeSet<>();
    private Set<String> imagens = new HashSet<>();
    private Set<CaracteristicaProdutoResponse> caracteristicasProduto = new HashSet<>();

    public DetalhesProdutoResponse(Produto produto) {
        this.nomeProduto = produto.getNome();
        this.precoProduto = produto.getValor();
        this.descricaoProduto = produto.getDescricao();

        this.opinioesProduto = produto.mapeiaOpinioes(OpiniaoResponse::new);

        this.mediaNotaProduto = media(produto);

        this.totalNotasProduto = this.opinioesProduto.size();

        this.imagens.addAll(produto.mapeiaImagens(imagemProduto -> imagemProduto.getLink()));

        this.caracteristicasProduto = produto.mapeiaCaracteristicas(CaracteristicaProdutoResponse::new);

        this.perguntasProduto.addAll(produto.mapeiaPerguntas(PerguntaResponse::new));
    }

    public double media(Produto produto) {
        Set<Integer> notas = produto.mapeiaOpinioes(opiniao -> opiniao.getNota());
        OptionalDouble possivelMedia = notas.stream().mapToInt(nota -> nota).average();
        return possivelMedia.orElse(0.0);
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(BigDecimal precoProduto) {
        this.precoProduto = precoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Set<OpiniaoResponse> getOpinioesProduto() {
        return opinioesProduto;
    }

    public void setOpinioesProduto(Set<OpiniaoResponse> opinioesProduto) {
        this.opinioesProduto = opinioesProduto;
    }

    public double getMediaNotaProduto() {
        return mediaNotaProduto;
    }

    public void setMediaNotaProduto(double mediaNotaProduto) {
        this.mediaNotaProduto = mediaNotaProduto;
    }

    public int getTotalNotasProduto() {
        return totalNotasProduto;
    }

    public void setTotalNotasProduto(int totalNotasProduto) {
        this.totalNotasProduto = totalNotasProduto;
    }

    public SortedSet<PerguntaResponse> getPerguntasProduto() {
        return perguntasProduto;
    }

    public void setPerguntasProduto(SortedSet<PerguntaResponse> perguntasProduto) {
        this.perguntasProduto = perguntasProduto;
    }

    public Set<String> getImagens() {
        return imagens;
    }

    public void setImagens(Set<String> imagens) {
        this.imagens = imagens;
    }

    public Set<CaracteristicaProdutoResponse> getCaracteristicasProduto() {
        return caracteristicasProduto;
    }

    public void setCaracteristicasProduto(Set<CaracteristicaProdutoResponse> caracteristicasProduto) {
        this.caracteristicasProduto = caracteristicasProduto;
    }
}
