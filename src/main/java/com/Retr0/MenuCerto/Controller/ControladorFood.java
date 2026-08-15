package com.Retr0.MenuCerto.Controller;


import com.Retr0.MenuCerto.Model.ProductDTO;
import com.Retr0.MenuCerto.Model.ProductResponseDto;
import com.Retr0.MenuCerto.Service.ConsumoApi;
import com.Retr0.MenuCerto.Service.convertedados;


import java.util.Scanner;

public class ControladorFood {
    ConsumoApi consumindo = new ConsumoApi();
    convertedados conversor = new convertedados();
    private final String Endereco = "https://world.openfoodfacts.org";
    Scanner leitura = new Scanner(System.in);


    public void Principal(){
        var regra = -1;
        while(regra !=0) {
            System.out.println("Bem-vindo ao Menu-Certo");
            System.out.println("Escolha uma Opção:");
            System.out.println("1-Buscar um Produto por código de barra");
            System.out.println("2-Buscar Produto pelo nome");
            System.out.println("3-Buscar por Filtro");
            System.out.println("Digite 0 para Sair");
            regra = leitura.nextInt();
            leitura.nextLine();


            switch (regra){
                case 1:
                    buscarporcodigo();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }


    }


    //Módulos
    private void buscarporcodigo(){
        System.out.println("Digite o número do Código de barras");
        var codigo = leitura.nextLine().toLowerCase();
        leitura.nextLine();
        var caminhocompleto = Endereco + ("/api/v2/product/" + codigo + ".json");

        var json = consumindo.obterDados(caminhocompleto);
        ProductResponseDto resposta = conversor.obterdados(json, ProductResponseDto.class);

        if (resposta != null && resposta.status() == 1) {
            System.out.println("\n Status: Produto Encontrado!");
            System.out.println("Código: " + resposta.code());

            ProductDTO produto = resposta.product();

            if (produto != null) {
                System.out.println("Nome do Produto: " + produto.productName());
                System.out.println("Marca: " + produto.brands());
                System.out.println("Nutri-Score: " + (produto.nutriscoreGrade() != null ? produto.nutriscoreGrade().toUpperCase() : "N/A"));
                System.out.println("Quantidade: " + produto.quantity());
                System.out.println("Ingredientes: " + produto.ingredientsText());
                System.out.println("Imagem: " + produto.imageUrl());

                if (produto.nutriments() != null) {
                    System.out.println("Calorias (100g): " + produto.nutriments().energyKcal100g() + " kcal");
                }
            }
        } else {
            System.out.println("\n Status: Produto não encontrado para o código fornecido.");
        }
    }

    //Criar metodo para buscar por nome
    //buscar metodo para buscar por filtro
    }














