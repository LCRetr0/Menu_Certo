package com.Retr0.MenuCerto.Model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponseDto(
        @JsonProperty("code")
        String code,

        @JsonProperty("status")
        int status,

        @JsonProperty("status_verbose")
        String statusVerbose,

        @JsonProperty("product")
        ProductDTO product
) {

    public boolean foiEncontrado() {
        return status == 1 && product != null;
    }
}