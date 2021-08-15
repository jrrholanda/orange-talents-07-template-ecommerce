package br.com.zup.mercadolivre.usuario;

import br.com.zup.mercadolivre.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class VerificaUsuarioLogado {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenManager tokenManager;

    public Usuario getUsuarioRequest(HttpServletRequest request){
        String userEmail = null;
        Optional<String> possibleToken = Optional.ofNullable(request.getHeader("Authorization"));
        if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {
            userEmail = tokenManager.getUserName(possibleToken.get());
        }
        return usuarioRepository.findByEmail(userEmail).get();
    }
}
