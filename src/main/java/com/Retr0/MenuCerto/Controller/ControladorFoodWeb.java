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
                .append("&page_size=10");

        // Aplica os filtros dinamicamente conforme enviados pelo front-end
        if (nutriscore != null && !nutriscore.isBlank()) {
            urlFinal.append("&nutriscore_grade_tags=").append(nutriscore.toLowerCase());
        }

        if (nova != null) {
            // No caso de busca padrão usa 'nova_group', se for outro país usa 'nova_group_tags'
            if (pais.equalsIgnoreCase("brazil")) {
                urlFinal.append("&nova_group=").append(nova);
            } else {
                urlFinal.append("&nova_group_tags=").append(nova);
            }
        }

        if (termo != null && !termo.isBlank()) {
            urlFinal.append("&categories_tags=").append(termo.trim().replace(" ", "+"));
        }

        if (restricao != null && !restricao.isBlank()) {
            if (restricao.equalsIgnoreCase("gluten")) {
                urlFinal.append(pais.equalsIgnoreCase("brazil") ? "&labels_tags_en=gluten-free" : "&labels_tags_en=en:gluten-free");
            } else if (restricao.equalsIgnoreCase("lactose")) {
                urlFinal.append(pais.equalsIgnoreCase("brazil") ? "&labels_tags_en=lactose-free" : "&labels_tags_en=en:lactose-free");
            }
        }

        String jsonString = consumindo.obterDados(urlFinal.toString());
        return conversor.obterdados(jsonString, SearchResponseDto.class);
    }
}