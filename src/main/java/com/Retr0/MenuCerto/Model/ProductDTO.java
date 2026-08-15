package com.Retr0.MenuCerto.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDTO(
        @JsonProperty("code")
        String code,

        @JsonProperty("product_name")
        String productName,

        @JsonProperty("product_name_pt")
        String productNamePt,

        @JsonProperty("brands")
        String brands,

        @JsonProperty("brands_tags")
        List<String> brandsTags,

        @JsonProperty("quantity")
        String quantity,

        @JsonProperty("serving_size")
        String servingSize,

        @JsonProperty("categories")
        String categories,

        @JsonProperty("categories_tags")
        List<String> categoriesTags,

        @JsonProperty("countries")
        String countries,

        @JsonProperty("countries_tags")
        List<String> countriesTags,

        @JsonProperty("labels")
        String labels,

        @JsonProperty("labels_tags")
        List<String> labelsTags,

        // Classificações e Pontuações
        @JsonProperty("nutriscore_grade")
        String nutriscoreGrade,

        @JsonProperty("nutriscore_score")
        Integer nutriscoreScore,

        @JsonProperty("nova_group")
        Integer novaGroup,

        @JsonProperty("ecoscore_grade")
        String ecoscoreGrade,

        // Ingredientes e Alergênicos
        @JsonProperty("ingredients_text")
        String ingredientsText,

        @JsonProperty("ingredients_text_pt")
        String ingredientsTextPt,

        @JsonProperty("allergens")
        String allergens,

        @JsonProperty("allergens_tags")
        List<String> allergensTags,

        @JsonProperty("traces")
        String traces,

        @JsonProperty("traces_tags")
        List<String> tracesTags,

        @JsonProperty("additives_tags")
        List<String> additivesTags,

        @JsonProperty("additives_n")
        Integer additivesCount,

        // URLs das Imagens
        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("image_front_url")
        String imageFrontUrl,

        @JsonProperty("image_ingredients_url")
        String imageIngredientsUrl,

        @JsonProperty("image_nutrition_url")
        String imageNutritionUrl,

        // Tabela Nutricional
        @JsonProperty("nutriments")
        NutrimentsDTO nutriments
) { }