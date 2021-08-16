package br.com.zup.mercadolivre.opiniao;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.produto.ProdutoResponse;
import br.com.zup.mercadolivre.security.UsuarioLogado;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/opinioes")
public class OpiniaoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping(value = "/produtos/{id}/opiniao")
    @Transactional
    public ResponseEntity<OpiniaoResponse> adiciona(@RequestBody @Valid NovaOpiniaoRequest opiniaoRequest,
                                   @PathVariable("id") Long id, @AuthenticationPrincipal UsuarioLogado usuarioLogado ) {

        Optional<Produto> produto = produtoRepository.findById(id);

        Usuario consumidor = usuarioLogado.get();

        Opiniao novaOpiniao = opiniaoRequest.toModel(produto, consumidor);
        manager.persist(novaOpiniao);

        try {
            return ResponseEntity.ok().body(new OpiniaoResponse(novaOpiniao));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
