package br.com.zup.mercadolivre.compra;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @NotNull @Valid
    private Compra compra;
    @NotBlank
    private String gatewayId;
    @Enumerated @NotNull
    private PagamentoStatus status;
    @CreationTimestamp
    private LocalDateTime instantePagamento;

    @Deprecated
    public Pagamento() {
    }

    public Pagamento(@NotNull @Valid Compra compra, @NotBlank String gatewayId, @NotNull PagamentoStatus status) {
        this.compra = compra;
        this.gatewayId = gatewayId;
        this.status = status;
        this.instantePagamento = LocalDateTime.now();
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public PagamentoStatus getStatus() {
        return status;
    }

    public Long getCompraId(){
        return compra.getId();
    }

    public boolean concluidoComSucesso(){
        return this.status.equals(PagamentoStatus.SUCESSO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id) && Objects.equals(compra, pagamento.compra) && Objects.equals(getGatewayId(), pagamento.getGatewayId()) && getStatus() == pagamento.getStatus() && Objects.equals(instantePagamento, pagamento.instantePagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compra, getGatewayId(), getStatus(), instantePagamento);
    }
}
