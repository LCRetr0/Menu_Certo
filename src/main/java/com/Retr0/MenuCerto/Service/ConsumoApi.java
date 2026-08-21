package com.Retr0.MenuCerto.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public String obterDados(String endereco) {
       
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .header("User-Agent", "MenuCertoApp/1.0 (contato@menucerto.com)")
                .uri(URI.create(endereco))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status da resposta HTTP: " + response.statusCode());

            if (response.statusCode() != 200) {
                System.err.println("Erro na chamada da API: Código " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Falha ao consumir API: " + e.getMessage(), e);
        }

        return response.body();
    }
}