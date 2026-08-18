package com.Retr0.MenuCerto.Controller;


import com.Retr0.MenuCerto.Model.ProductDTO;
import com.Retr0.MenuCerto.Model.ProdutoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponseDto(
        List<ProdutoDto> products
) {}