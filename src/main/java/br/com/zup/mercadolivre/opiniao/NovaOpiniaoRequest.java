package br.com.zup.mercadolivre.opiniao;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Optional;

public class NovaOpiniaoRequest {

    @Min(1) @Max(5)
    private int nota;
    @NotBlank
    private String titulo;
    @NotBlank @Size(max = 500)
    private String descricao;

    public NovaOpiniaoRequest(@Min(1) @Max(5) int nota, @NotBlank String titulo,
                              @NotBlank @Size(max = 500) String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Opiniao toModel(Optional<Produto> produto, @NotNull @Valid Usuario consumidor) {
        return new Opiniao(nota, titulo, descricao,produto.get(),consumidor);
    }

}
