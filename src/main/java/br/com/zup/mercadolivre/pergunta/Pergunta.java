package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NotBlank String titulo;

    @ManyToOne
    private @NotNull @Valid Usuario usuarioInteressado;

    @ManyToOne
    private @NotNull @Valid Produto produto;

    @CreationTimestamp
    private LocalDate dataPergunta;

    @Deprecated
    public Pergunta() {

    }

    public Pergunta(@NotBlank String titulo,
                    @NotNull @Valid Usuario usuarioInteressado,
                    @NotNull @Valid Produto produto) {
        this.titulo = titulo;
        this.usuarioInteressado = usuarioInteressado;
        this.produto = produto;
        this.dataPergunta = LocalDate.now();
    }


    public String getTitulo() {
        return titulo;
    }

    public Usuario getUsuarioInteressado() {
        return usuarioInteressado;
    }

    public Usuario getDonoProduto() {
        return produto.getDono();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((usuarioInteressado == null) ? 0 : usuarioInteressado.hashCode());
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pergunta other = (Pergunta) obj;
        if (usuarioInteressado == null) {
            if (other.usuarioInteressado != null)
                return false;
        } else if (!usuarioInteressado.equals(other.usuarioInteressado))
            return false;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        return true;
    }

}
