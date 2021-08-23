package br.com.zup.mercadolivre.compra;

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class Ranking implements EventoCompraSucesso{

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(),"A compra não está concluída "+compra);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idDonoProduto", compra.getDonoProduto().getId());

        restTemplate.postForEntity("http://localhost:8080/ranking",
                request, String.class);
    }
}
