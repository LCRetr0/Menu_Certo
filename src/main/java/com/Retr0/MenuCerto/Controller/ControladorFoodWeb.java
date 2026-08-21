package com.Retr0.MenuCerto.Controller;

import com.Retr0.MenuCerto.Model.ProductDTO;
import com.Retr0.MenuCerto.Model.ProductResponseDto;
import com.Retr0.MenuCerto.Controller.SearchResponseDto;
import com.Retr0.MenuCerto.Service.ConsumoApi;
import com.Retr0.MenuCerto.Service.convertedados;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ControladorFoodWeb {

    private final ConsumoApi consumindo = new ConsumoApi();
    private final convertedados conversor = new convertedados();
    private final String ENDERECO_BASE = "https://world.openfoodfacts.org";

    // 1. Busca de Produto por Código de Barras
    @GetMapping("/codigo/{codigo}")
    public ProductDTO buscarPorCodigo(@PathVariable String codigo) {
        String caminhoCompleto = ENDERECO_BASE + "/api/v2/product/" + codigo.toLowerCase() + ".json";
        String json = consumindo.obterDados(caminhoCompleto);

        ProductResponseDto resposta = conversor.obterdados(json, ProductResponseDto.class);

        if (resposta != null && resposta.status() == 1) {
            return resposta.product();
        }
        return null;
    }

    // 2. Busca por Categorias e Filtros
    @GetMapping("/buscar")
    public SearchResponseDto buscarPorCategoria(
            @RequestParam(defaultValue = "brazil") String pais,
            @RequestParam(required = false) String nutriscore,
            @RequestParam(required = false) Integer nova,
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) String restricao) {


        StringBuilder urlFinal = new StringBuilder(ENDERECO_BASE)
                .append("/api/v2/search.json?countries_tags_en=")
                .append(pais.toLowerCase())
                .append("&page_size=20");

        // Aplica os filtros dinamicamente conforme enviados pelo front-end
        if (nutriscore != null && !nutriscore.isBlank()) {
            urlFinal.append("&nutriscore_grade_tags=").append(nutriscore.toLowerCase().trim());
        }

        if (nova != null) {
            urlFinal.append("&nova_groups_tags=").append(nova);
        }

        if (termo != null && !termo.isBlank()) {
            String termoFormatado = termo.trim().replace(" ", "+");
            urlFinal.append("&categories_tags=").append(termo.trim().replace(" ", "+"));
        }

        if (restricao != null && !restricao.isBlank()) {
            if (restricao.equalsIgnoreCase("gluten")) {
                urlFinal.append("&labels_tags_en=en:gluten-free");
            } else if (restricao.equalsIgnoreCase("lactose")) {
                urlFinal.append("&labels_tags_en=en:no-lactose");
            }
        }
        urlFinal.append("&fields=code,product_name,product_name_pt,brands,image_front_small_url,nutriscore_grade");
        String jsonString = consumindo.obterDados(urlFinal.toString());
        return conversor.obterdados(jsonString, SearchResponseDto.class);
    }
}