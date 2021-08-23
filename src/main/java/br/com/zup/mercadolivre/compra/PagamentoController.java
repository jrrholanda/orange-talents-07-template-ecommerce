package br.com.zup.mercadolivre.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PagamentoController {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EventoCompraSucessoService eventosNovaCompra;

    @PostMapping(value = "/paypal/{id}")
    @Transactional
    public ResponseEntity<?> pagamentoPaypal(@PathVariable("id") Long idCompra, @Valid PaypalRequest paypalRequest){
        processa(idCompra, paypalRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/pagseguro/{id}")
    @Transactional
    public ResponseEntity<?> pagamentoPagseguro(@PathVariable("id") Long idCompra, @Valid PagseguroRequest pagseguroRequest){
        processa(idCompra, pagseguroRequest);
        return ResponseEntity.ok().build();
    }

    private String processa(Long idCompra, GatewayPagamentoRequest pagamentoRequest){
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        compra.adicionaPagamento(pagamentoRequest);
        compraRepository.save(compra);
        eventosNovaCompra.processa(compra);

        return compra.toString();
    }
}
