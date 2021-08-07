package br.com.zup.mercadolivre.usuario;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioResponse> post (@RequestBody @Valid UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioRequest.toModel();
        String senhaEncoder = DigestUtils.sha256Hex(usuario.getSenha());
        usuario.setSenha(senhaEncoder);
        usuarioRepository.save(usuario);
        try {
            return ResponseEntity.ok().body(new UsuarioResponse(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
