package com.Retr0.MenuCerto.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NutrimentsDTO(
        // Calorias / Energia
        @JsonProperty("energy-kcal_100g")
        Double energyKcal100g,

        @JsonProperty("energy-kcal_serving")
        Double energyKcalServing,

        // Carboidratos
        @JsonProperty("carbohydrates_100g")
        Double carbohydrates100g,

        @JsonProperty("carbohydrates_serving")
        Double carbohydratesServing,

        // Açúcares
        @JsonProperty("sugars_100g")
        Double sugars100g,

        @JsonProperty("sugars_serving")
        Double sugarsServing,

        // Proteínas
        @JsonProperty("proteins_100g")
        Double proteins100g,

        @JsonProperty("proteins_serving")
        Double proteinsServing,

        // Gorduras Totais
        @JsonProperty("fat_100g")
        Double fat100g,

        @JsonProperty("fat_serving")
        Double fatServing,

        // Gorduras Saturadas
        @JsonProperty("saturated-fat_100g")
        Double saturatedFat100g,

        @JsonProperty("saturated-fat_serving")
        Double saturatedFatServing,

        // Gorduras Trans
        @JsonProperty("trans-fat_100g")
        Double transFat100g,

        // Fibras
        @JsonProperty("fiber_100g")
        Double fiber100g,

        @JsonProperty("fiber_serving")
        Double fiberServing,

        // Sódio e Sal
        @JsonProperty("sodium_100g")
        Double sodium100g,

        @JsonProperty("salt_100g")
        Double salt100g,

        // Micronutrientes
        @JsonProperty("calcium_100g")
        Double calcium100g,

        @JsonProperty("iron_100g")
        Double iron100g
) {}