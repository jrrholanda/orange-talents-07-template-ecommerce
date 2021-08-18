package br.com.zup.mercadolivre.produto;

import java.util.HashSet;
import java.util.Set;

public class ImagemProdutoResponse {

    private Set<String> images = new HashSet<>();

    public ImagemProdutoResponse(Produto produto) {
        this.images.addAll(produto.mapeiaImagens(image -> image.getLink()));
    }
}
