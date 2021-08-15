package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.categoria.Categoria;
import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.validacao.ExistsId;
import br.com.zup.mercadolivre.validacao.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;

public class ProdutoRequest {

    @NotBlank @UniqueValue(domainClass = Produto.class, fieldName = "nome")
    private String nome;
    @NotNull @Positive
    private BigDecimal valor;
    @NotNull @Positive
    private Integer quantidade;
    @NotBlank @Length(max=1000)
    private String descricao;
    @NotNull @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;
    @Size(min=3) @Valid
    private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProdutoRequest(@NotBlank String nome, @NotNull @Positive BigDecimal valor, @NotNull @Positive Integer quantidade,
                          @NotBlank String descricao, @NotNull Long idCategoria,
                          @Size(min = 3) @Valid List<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.caracteristicas.addAll(caracteristicas);
    }

    public List<NovaCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<NovaCaracteristicaRequest> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Produto toModel(EntityManager manager, Usuario dono) {
        Categoria categoria = manager.find(Categoria.class, this.idCategoria);

        return new Produto(this.nome, this.valor, this.quantidade, this.descricao, categoria,
               dono, this.caracteristicas);
    }


    public Set<String> buscaCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();

        for (NovaCaracteristicaRequest caracteristica : caracteristicas) {
            String nome = caracteristica.getNome();

            if (!nomesIguais.add(nome)) {
                resultados.add(nome);
            }
        }
        return resultados;
    }
}
