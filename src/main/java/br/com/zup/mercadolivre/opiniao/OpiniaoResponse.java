package br.com.zup.mercadolivre.opiniao;

public class OpiniaoResponse {

    private int nota;
    private String titulo;
    private String descricao;

    public OpiniaoResponse(Opiniao novaOpiniao) {
        this.titulo = novaOpiniao.getTitulo();
        this.nota = novaOpiniao.getNota();
        this.descricao = novaOpiniao.getDescricao();
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
