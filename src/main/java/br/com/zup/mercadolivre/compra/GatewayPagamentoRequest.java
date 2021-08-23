package br.com.zup.mercadolivre.compra;

public interface GatewayPagamentoRequest {

    Pagamento toPagamento(Compra compra);
}
