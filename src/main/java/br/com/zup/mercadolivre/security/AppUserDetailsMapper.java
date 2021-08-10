package br.com.zup.mercadolivre.security;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper{

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario)shouldBeASystemUser);
    }

}
