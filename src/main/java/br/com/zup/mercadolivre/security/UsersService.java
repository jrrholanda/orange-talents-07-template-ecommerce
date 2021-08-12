package br.com.zup.mercadolivre.security;

import java.util.List;
import java.util.Optional;

import br.com.zup.mercadolivre.usuario.Usuario;
import br.com.zup.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Optional<Usuario> user = userRepository.findByEmail(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(userName + "not found."));
        return userDetailsMapper.map(user.get());
    }

}
