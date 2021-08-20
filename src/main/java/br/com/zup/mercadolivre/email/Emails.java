package br.com.zup.mercadolivre.email;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.pergunta.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
        mailer.send("<html>...</html>","Nova pergunta...",pergunta.getUsuarioInteressado().getEmail(),"novapergunta@mercadolivre.com",pergunta.getDonoProduto().getEmail());
    }


    public void novaCompra(@NotNull @Valid Compra compra) {
        mailer.send("<html>...</html>", "Nova compra...",compra.getComprador().getEmail(), "novacompra@mercadolivre.com",compra.getDonoProduto().getEmail());
    }
}

