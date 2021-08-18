package br.com.zup.mercadolivre.pergunta;

import java.util.Comparator;

public class PerguntaResponse implements Comparable<PerguntaResponse>{

    private String titulo;

    public PerguntaResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public int compareTo(PerguntaResponse o) {
        return Comparator.comparing(PerguntaResponse::getTitulo).reversed().compare(this, o);
    }
}
