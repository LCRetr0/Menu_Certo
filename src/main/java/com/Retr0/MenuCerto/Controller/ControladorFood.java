package com.Retr0.MenuCerto.Controller;


import com.Retr0.MenuCerto.Model.ProductDTO;
import com.Retr0.MenuCerto.Model.ProductResponseDto;
import com.Retr0.MenuCerto.Model.ProdutoDto;
import com.Retr0.MenuCerto.Service.ConsumoApi;
import com.Retr0.MenuCerto.Service.convertedados;


import java.util.InputMismatchException;
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
            System.out.println("2-Buscar Produto por categoria");
            System.out.println("Digite 0 para Sair");
            regra = leitura.nextInt();
            leitura.nextLine();


            switch (regra){
                case 1:
                    buscarporcodigo();
                    break;
                case 2:
                    buscarpornome();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    System.out.println("Obrigado por utilizar :)");
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
                System.out.println("");
            }
        } else {
            System.out.println("\n Status: Produto não encontrado para o código fornecido.");
        }
    }

    public void buscarpornome() {
        System.out.println("\n--- Escolha o tipo de filtro para a busca ---");
        System.out.println("Ele retornará apenas os 10 de cada categoria, do Brasil");
        System.out.println("1 - Qualidade Nutricional (Nutri-Score A-E)");
        System.out.println("2 - Nível de Processamento (NOVA Group 1-4)");
        System.out.println("3 - Tipos de Alimentos (Categorias)");
        System.out.println("4 - Restrições Alimentares (Alergênicos)");
        System.out.println("5 - Produtos de outros Países País (Ex: Brasil)");
        System.out.print("Digite a opção desejada: ");

        if (!leitura.hasNextInt()) {
            System.out.println("Opção inválida! Digite apenas números.");
            leitura.nextLine();
            return;
        }

        int escolha = leitura.nextInt();
        leitura.nextLine();

        String URL_BASE_BUSCA = Endereco + "/api/v2/search.json?countries_tags_en=brazil&page_size=10";
        String urlFinal = "";

        try {
            switch (escolha) {
                case 1:
                    System.out.print("Escolha a letra do Nutri-Score (a, b, c, d, e): ");
                    String score = leitura.nextLine().trim().toLowerCase();
                    urlFinal = URL_BASE_BUSCA + "&nutriscore_grade_tags=" + score;
                    break;

                case 2:
                    System.out.println("Escolha o grupo NOVA (1: In natura, 2: Processado, 3: Mais Processado, 4: Ultraprocessado):");
                    int nova = leitura.nextInt();
                    leitura.nextLine();
                    urlFinal = URL_BASE_BUSCA + "&nova_group=" + nova;
                    break;

                case 3:
                    System.out.print("Digite o nome ou tipo de alimento (ex: chocolate, leite, biscoito): ");
                    String termo = leitura.nextLine().trim().replace(" ", "+");
                    urlFinal = URL_BASE_BUSCA + "&categories_tags=" + termo;
                    break;

                case 4:
                    System.out.println("Escolha a restrição:\n1 - Sem Glúten\n2 - Sem Lactose");
                    int restricao = leitura.nextInt();
                    leitura.nextLine();
                    if (restricao == 1) {
                        urlFinal = URL_BASE_BUSCA + "&labels_tags_en=gluten-free";
                    } else {
                        urlFinal = URL_BASE_BUSCA + "&labels_tags_en=lactose-free";
                    }
                    break;

                case 5:
                    System.out.print("Digite o país em inglês (ex: brazil, france): ");
                    String pais = leitura.nextLine().trim().toLowerCase();

                    while (pais.isEmpty()) {
                        System.out.print("País inválido! Digite novamente (ex: brazil, france): ");
                        pais = leitura.nextLine().trim().toLowerCase();
                    }

                    String urlBasePais = Endereco + "/api/v2/search.json?countries_tags_en=" + pais + "&page_size=10";

                    System.out.println("\n--- Escolha o tipo de filtro para " + pais.toUpperCase() + " ---");
                    System.out.println("1 - Qualidade Nutricional (Nutri-Score A-E)");
                    System.out.println("2 - Nível de Processamento (NOVA Group 1-4)");
                    System.out.println("3 - Tipos de Alimentos (Categorias)");
                    System.out.println("4 - Restrições Alimentares (Alergênicos)");
                    System.out.print("Digite a opção: ");

                    if (!leitura.hasNextInt()) {
                        System.out.println("Opção inválida! Digite apenas números.");
                        leitura.nextLine();
                        return;
                    }

                    int opcaoPais = leitura.nextInt();
                    leitura.nextLine();

                    switch (opcaoPais) {
                        case 1:
                            System.out.print("Escolha a letra do Nutri-Score (a, b, c, d, e): ");
                            String scorePais = leitura.nextLine().trim().toLowerCase();
                            urlFinal = urlBasePais + "&nutriscore_grade_tags=" + scorePais;
                            break;

                        case 2:
                            System.out.print("Escolha o grupo NOVA (1 a 4): ");
                            if (!leitura.hasNextInt()) {
                                System.out.println("Valor inválido para o grupo NOVA!");
                                leitura.nextLine();
                                return;
                            }
                            int novaPais = leitura.nextInt();
                            leitura.nextLine();
                            urlFinal = urlBasePais + "&nova_group_tags=" + novaPais;
                            break;

                        case 3:
                            System.out.print("Digite o nome ou tipo de alimento: ");
                            String termoPais = leitura.nextLine().trim().replace(" ", "+");
                            urlFinal = urlBasePais + "&categories_tags=" + termoPais;
                            break;

                        case 4:
                            System.out.println("Escolha a restrição:\n1 - Sem Glúten\n2 - Sem Lactose");
                            if (!leitura.hasNextInt()) {
                                System.out.println("Opção inválida!");
                                leitura.nextLine();
                                return;
                            }
                            int restricaoPais = leitura.nextInt();
                            leitura.nextLine();
                            if (restricaoPais == 1) {
                                urlFinal = urlBasePais + "&labels_tags_en=en:gluten-free";
                            } else if (restricaoPais == 2) {
                                urlFinal = urlBasePais + "&labels_tags_en=en:lactose-free";
                            } else {
                                System.out.println("Opção de restrição inválida!");
                                return;
                            }
                            break;

                        default:
                            System.out.println("Opção inválida!");
                            return;
                    }
                    break;

                default:
                    System.out.println("Opção inválida!");
                    return;
            }} catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Digite o tipo de dado correto.");
            leitura.nextLine();
            return;
        }

        System.out.println("\nBuscando produtos...");
        var jsonString = consumindo.obterDados(urlFinal);
        SearchResponseDto resultado = conversor.obterdados(jsonString, SearchResponseDto.class);
        if (resultado.products() == null || resultado.products().isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
            for (ProdutoDto produto : resultado.products()) {
                System.out.println(produto);
            }
        }
    }

    }














