package br.com.zup.mercadolivre.compra;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalRequest implements GatewayPagamentoRequest{

    @NotBlank
    private String gatewayId;

    @NotNull @Min(0) @Max(1)
    private Integer status;

    public PaypalRequest(String gatewayId, Integer status) {
        this.gatewayId = gatewayId;
        this.status = status;
    }

    @Override
    public Pagamento toPagamento(Compra compra) {
        return new Pagamento(compra, gatewayId, toStatus());
    }

    private PagamentoStatus toStatus() {
        if(status == 1)
            return PagamentoStatus.SUCESSO;
        return PagamentoStatus.ERRO;
    }
}
