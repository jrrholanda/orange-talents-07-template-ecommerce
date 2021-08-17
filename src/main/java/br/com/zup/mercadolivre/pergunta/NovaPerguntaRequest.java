package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaPerguntaRequest {
    @NotBlank
    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(@NotNull @Valid Usuario usuarioInteressado, @NotNull @Valid Produto produto) {

        return new Pergunta(titulo, usuarioInteressado, produto);
    }
}
