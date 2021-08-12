package br.com.zup.mercadolivre.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PersistenceContext
    private EntityManager manager;

    @InitBinder(value = "produtoRequest")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> post (@RequestBody @Valid ProdutoRequest produtoRequest) {
        Produto produto = produtoRequest.toModel(manager);
        produtoRepository.save(produto);
        try {
            return ResponseEntity.ok().body(new ProdutoResponse(produto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
