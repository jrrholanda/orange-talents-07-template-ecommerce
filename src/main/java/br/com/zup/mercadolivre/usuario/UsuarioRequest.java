package br.com.zup.mercadolivre.usuario;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank
    @Email
    @Column(unique = true)
    private String login;
    @NotBlank @Size(min=6)
    private String senha;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UsuarioRequest(@NotBlank @Email String login, @NotBlank @Size(min=6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario toModel() {
        return new Usuario(this.login, this.senha);
    }
}
