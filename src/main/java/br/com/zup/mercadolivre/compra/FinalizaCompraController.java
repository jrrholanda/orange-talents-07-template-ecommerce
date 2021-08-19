package br.com.zup.mercadolivre.compra;

import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.security.UsuarioLogado;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/compras")
public class FinalizaCompraController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CompraRepository compraRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> finalizaCompra(@RequestBody @Valid NovaCompraRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                                            UriComponentsBuilder uriComponentsBuilder) throws BindException{

        Usuario comprador = usuarioLogado.get();
        Produto produto = produtoRepository.findById(request.getIdProduto()).get();

        boolean estoque = produto.atualizaEstoque(request.getQuantidade());
        if(estoque) {

            Compra compra = request.toModel(produto, comprador);
            compraRepository.save(compra);

            var response = new HashMap<>();
            response.put("Url do pagamento", compra.geraUrlGateway(uriComponentsBuilder));
            return ResponseEntity.ok(response);
        }


        BindException problemaComEstoque = new BindException(request,
                "novaCompraRequest");
        problemaComEstoque.reject(null,
                "Não há estoque para o produto solicitado");

        throw problemaComEstoque;
    }
}
