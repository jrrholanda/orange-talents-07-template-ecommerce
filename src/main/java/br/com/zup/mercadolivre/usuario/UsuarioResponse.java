package br.com.zup.mercadolivre.usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private String email;
    private LocalDateTime dataCadastro;

    public UsuarioResponse(Usuario usuario) {
        this.email = usuario.getEmail();
        this.dataCadastro = usuario.getDataCadastro();
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
