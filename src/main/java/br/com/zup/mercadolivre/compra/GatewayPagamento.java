package br.com.zup.mercadolivre.compra;

import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamento {

    PAGSEGURO{
        @Override
        String criaUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder){
            String urlPagseguro = uriComponentsBuilder.path("/pagseguro/{id}")
                    .buildAndExpand(compra.getId()).toString();

            return "pagseguro.com?returnId=" + compra.getId() + "&redirectUrl="+ urlPagseguro;
        }
    },

    PAYPAL{
        @Override
        String criaUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder){
            String urlPaypal = uriComponentsBuilder.path("/paypal/{id}")
                    .buildAndExpand(compra.getId()).toString();

            return "paypal.com?buyerId=" + compra.getId() + "&redirectUrl=" + urlPaypal;
        }
    };

    abstract String criaUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}
