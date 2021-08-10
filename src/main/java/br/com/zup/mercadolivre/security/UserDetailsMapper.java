package br.com.zup.mercadolivre.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMapper {

    UserDetails map(Object shouldBeASystemUser);
}
