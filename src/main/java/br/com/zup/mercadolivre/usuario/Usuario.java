package br.com.zup.mercadolivre.usuario;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Email @Column(unique = true)
    private String login;
    @NotBlank @Size(min=6)
    private String senha;
    @CreationTimestamp @NotNull
    private LocalDateTime dataCadastro = LocalDateTime.now();

    public Usuario(@NotBlank @Email String login, @NotBlank @Size(min=6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
