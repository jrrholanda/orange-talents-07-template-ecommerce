package br.com.zup.mercadolivre.compra;

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class NotaFiscal implements EventoCompraSucesso{

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(),"A compra não está concluída "+compra);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idComprador", compra.getComprador().getId());

        restTemplate.postForEntity("http://localhost:8080/notas-fiscais",
                request, String.class);
    }
}
