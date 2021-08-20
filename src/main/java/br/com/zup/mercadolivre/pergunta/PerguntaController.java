package br.com.zup.mercadolivre.pergunta;

import br.com.zup.mercadolivre.email.Emails;
import br.com.zup.mercadolivre.produto.Produto;
import br.com.zup.mercadolivre.produto.ProdutoRepository;
import br.com.zup.mercadolivre.security.UsuarioLogado;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/perguntas")
public class PerguntaController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private Emails emails;

    @PostMapping(value = "/api/produtos/{id}/perguntas")
    @Transactional

    public ResponseEntity<PerguntaResponse> cria(@RequestBody @Valid NovaPerguntaRequest perguntaRequest, @PathVariable("id") Long id, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Optional<Produto> produto = produtoRepository.findById(id);

        Usuario usuarioInteressado = usuarioLogado.get();

        Pergunta pergunta = perguntaRequest.toModel(usuarioInteressado,produto.get());

        perguntaRepository.save(pergunta);

        emails.novaPergunta(pergunta);

        try {
            return ResponseEntity.ok().body(new PerguntaResponse(pergunta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
