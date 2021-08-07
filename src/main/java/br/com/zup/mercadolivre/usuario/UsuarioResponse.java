package br.com.zup.mercadolivre.usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private String login;
    private LocalDateTime dataCadastro;

    public UsuarioResponse(Usuario usuario) {
        this.login = usuario.getLogin();
        this.dataCadastro = usuario.getDataCadastro();
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
