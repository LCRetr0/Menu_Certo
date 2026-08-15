# 🥗 Menu Certo

O **Menu Certo** é uma aplicação em Java desenvolvida no padrão de arquitetura **MVC (Model-View-Controller)** para consulta, análise e estruturação de dados nutricionais de produtos alimentícios.

A aplicação consome a API REST aberta e colaborativa do **Open Food Facts** para obter informações detalhadas sobre alimentos (como ingredientes, tabela nutricional, presença de alergênicos, grau de processamento e classificação **Nutri-Score**), permitindo a futura persistência dos dados consultados em um banco de dados relacional **MySQL**.

---

##Funcionalidades

- [x] **Busca por Código de Barras (EAN/UPC):** Consulta detalhada do produto e exibição de tabela nutricional por porção e 100g.
- [ ] **Busca de Produtos por Nome:** Pesquisa dinâmica no catálogo com paginação de resultados.
- [ ] **Filtros Personalizados:** Filtragem por marca, categoria e nota do Nutri-Score.
- [ ] **Persistência de Dados (MySQL):** Armazenamento de produtos consultados para histórico e relatórios locais.

---

##Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Serialização/Desserialização:** Jackson (`ObjectMapper`, `@JsonProperty`, `@JsonIgnoreProperties`)
- **Arquitetura:** MVC (Model-View-Controller)
- **Persistência (Em integração):** MySQL
- **Fonte de Dados Externa:** [Open Food Facts API](https://world.openfoodfacts.org/data)

---

## 🔌 API Consumida

A aplicação faz requisições HTTP para a API oficial do **Open Food Facts**:
* **Documentação Oficial:** [world.openfoodfacts.org/data](https://world.openfoodfacts.org/data)
* **URL Base:** `https://world.openfoodfacts.org`
* **Endpoint Principal (Consulta por EAN):** `GET /api/v2/product/{barcode}.json`

---

## Integração com Banco de Dados (MySQL)

O projeto está sendo preparado e em breve irá realizar o mapeamento relacional (JPA/Hibernate) e salvar as consultas no **MySQL**. A estrutura do banco armazena:

- **Tabela `produtos`:** `code`, `product_name`, `brands`, `quantity`, `nutriscore_grade`, `image_url`.
- **Tabela `nutrientes`:** Relação com calorias, carboidratos, proteínas, gorduras, sódio e açúcares.

---

## Estrutura do Projeto (MVC)

```text
src/main/java/com/Retr0/MenuCerto/
│
├── Controller/          # Fluxo de entrada e navegação dos menus
│   └── ControladorFood.java
│
├── Model/               # Mapeamento do JSON e entidades do banco
│   ├── ProductResponseDto.java
│   ├── ProductDTO.java
│   └── NutrimentsDTO.java
│
└── Service/             # Consumo da API HTTP e conversão de dados
    ├── ConsumoApi.java
    └── convertedados.java