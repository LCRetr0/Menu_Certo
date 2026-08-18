# 🥗 Menu Certo

O **Menu Certo** é uma aplicação em Java desenvolvida no padrão de arquitetura **MVC (Model-View-Controller)** para consulta, análise e estruturação de dados nutricionais de produtos alimentícios.

A aplicação consome a API REST aberta do **Open Food Facts** para obter informações detalhadas sobre alimentos (como ingredientes, tabela nutricional, alergênicos, grau de processamento NOVA e classificação **Nutri-Score**), permitindo a futura persistência dos dados consultados em um banco de dados relacional **MySQL**.

---

## Funcionalidades

- [x] **Busca por Código de Barras (EAN/UPC):** Consulta pontual do produto e exibição detalhada de tabela nutricional por porção/100g.
- [x] **Busca e Filtros Dinâmicos por Categoria/País:**
    - Filtragem por qualidade nutricional (**Nutri-Score** A–E).
    - Filtragem por nível de processamento (**Grupo NOVA** 1–4).
    - Filtragem por categorias e tipos de alimentos.
    - Filtragem por restrições alimentares (**Sem Glúten** / **Sem Lactose**).
    - Consulta customizada por países (**Brasil, França, Egito, etc.**).
- [ ] **Persistência de Dados (MySQL):** Armazenamento local de produtos consultados para relatórios e histórico.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+ / Java 25
- **Framework Base:** Spring Boot 3.2.x (`CommandLineRunner`)
- **Serialização/Desserialização:** Jackson (`ObjectMapper`, Records, DTOs)
- **Cliente HTTP:** Java `HttpClient` / `HttpRequest` com cabeçalho `User-Agent` personalizado
- **Arquitetura:** MVC (Model-View-Controller)
- **Persistência (Em integração):** MySQL / Spring Data JPA

---

## 🔌 API Consumida

A aplicação faz requisições HTTP para os endpoints da API **Open Food Facts**:
* **Documentação Oficial:** [world.openfoodfacts.org/data](https://world.openfoodfacts.org/data)
* **URL Base:** `https://world.openfoodfacts.org`
* **Endpoints Utilizados:**
    * **Consulta por EAN:** `GET /api/v2/product/{barcode}.json`
    * **Busca Parametrizada (Filtros/País):** `GET /api/v2/search.json?countries_tags_en={pais}&nova_groups_tags={grupo}&nutriscore_grade_tags={score}&page_size=10`

---

## 🗄️ Integração com Banco de Dados (MySQL)

O projeto está sendo preparado para realizar o mapeamento relacional (JPA/Hibernate) e persistir as consultas no **MySQL**. A estrutura prevê:

- **Tabela `produtos`:** `code`, `product_name`, `brands`, `quantity`, `nutriscore_grade`, `image_url`.
- **Tabela `nutrientes`:** Relação com calorias, carboidratos, proteínas, gorduras, sódio e açúcares.

---

## 📂 Estrutura do Projeto (MVC)

```text
src/main/java/com/Retr0/MenuCerto/
│
├── Controller/          # Lógica dos menus, leitura via Scanner e construção de URLs
│   └── ControladorFood.java
│
├── Model/               # Mapeamento dos DTOs/Records para desserialização do JSON
│   ├── ProductResponseDto.java
│   ├── ProductDTO.java
│   ├── ProdutoDto.java
│   ├── SearchResponseDto.java
│   └── NutrimentsDTO.java
│
└── Service/             # Requisições HTTP (ConsumoApi) e parsing com Jackson (convertedados)
    ├── ConsumoApi.java
    └── convertedados.java