package br.com.zup.mercadolivre.pergunta;

public class PerguntaResponse {

    private String titulo;

    public PerguntaResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }
}
