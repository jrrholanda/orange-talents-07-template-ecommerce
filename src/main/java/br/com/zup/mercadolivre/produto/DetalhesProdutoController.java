package br.com.zup.mercadolivre.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/produtos")
public class DetalhesProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/{id}/details")
    public ResponseEntity<DetalhesProdutoResponse> exibirDetalhes(@PathVariable("id") Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(new DetalhesProdutoResponse(produto));
    }
}
