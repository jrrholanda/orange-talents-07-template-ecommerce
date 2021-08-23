package br.com.zup.mercadolivre.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventoCompraSucessoService {

    @Autowired
    private Set<EventoCompraSucesso> eventosCompra;

    public void processa(Compra compra){
        if(compra.processadaComSucesso()){
            eventosCompra.forEach(evento -> evento.processa(compra));
        }
    }
}
