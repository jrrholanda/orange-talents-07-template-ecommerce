package br.com.zup.mercadolivre.categoria;

import br.com.zup.mercadolivre.validacao.ExistsId;
import br.com.zup.mercadolivre.validacao.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class CategoriaRequest {

    @NotBlank @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

    private Long idCategoria;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoriaRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria toModel(CategoriaRepository categoriaRepository){
        Categoria categoria = new Categoria(this.nome);
        if(this.idCategoria != null){
            Optional<Categoria> categoriaMae = categoriaRepository.findById(this.idCategoria);
            if(categoriaMae.isPresent()){
                categoria.setCategoria(categoriaMae.get());
            }
        }
        return categoria;
    }
}
