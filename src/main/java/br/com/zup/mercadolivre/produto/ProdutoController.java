package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.security.UsuarioLogado;
import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.usuario.VerificaUsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Uploader uploaderFake;

    @InitBinder(value = "produtoRequest")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> post (@RequestBody @Valid ProdutoRequest produtoRequest, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Usuario dono = usuarioLogado.get();

        Produto produto = produtoRequest.toModel(manager, dono);
        produtoRepository.save(produto);

        try {
            return ResponseEntity.ok().body(new ProdutoResponse(produto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<ImagemProdutoResponse> cadastraImagem(@PathVariable("id") Long idProduto, @Valid NovasImagensRequest imagensRequest, @AuthenticationPrincipal UsuarioLogado usuarioLogado){
        Usuario usuarioAtual = usuarioLogado.get();
        Optional<Produto> produto = produtoRepository.findById(idProduto);

        if(!produto.get().getDono().equals(usuarioAtual) ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploaderFake.envia(imagensRequest.getImagens());
        produto.get().associaImagens(links);
        produtoRepository.save(produto.get());

        try {
            return ResponseEntity.ok().body(new ImagemProdutoResponse(produto.get()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
