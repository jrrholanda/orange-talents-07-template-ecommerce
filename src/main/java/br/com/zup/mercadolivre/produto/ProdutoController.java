package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.usuario.VerificaUsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private VerificaUsuarioLogado verificaUsuarioLogado;

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
    public ResponseEntity<ProdutoResponse> post (@RequestBody @Valid ProdutoRequest produtoRequest, HttpServletRequest request) {
        Usuario dono = verificaUsuarioLogado.getUsuarioRequest(request);

        Produto produto = produtoRequest.toModel(manager, dono);
        produtoRepository.save(produto);

        try {
            return ResponseEntity.ok().body(new ProdutoResponse(produto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/imagem")
    public String cadastraImagem(@PathVariable Long idProduto, HttpServletRequest request, @Valid NovasImagensRequest imagensRequest){
        Usuario usuarioLogado = verificaUsuarioLogado.getUsuarioRequest(request);
        Optional<Produto> produto = produtoRepository.findById(idProduto);

        if(!produto.get().getDono().equals(usuarioLogado) ){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploaderFake.envia(imagensRequest.getImagens());
        produto.get().associaImagens(links);
        produtoRepository.save(produto.get());

        return "Imagem cadastrada";
    }
}
