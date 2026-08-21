# 🥗 Menu Certo

O **Menu Certo** é uma aplicação Java desenvolvida no padrão de arquitetura **MVC (Model-View-Controller)** para consulta, análise e estruturação de dados nutricionais de produtos alimentícios.

A aplicação consome a API REST aberta do **Open Food Facts** para obter informações detalhadas sobre alimentos (como ingredientes, marca, calorias, restrições e classificação **Nutri-Score**), contando com uma **interface Web moderna em Grid** integrada via **Spring Boot REST API**.

---

## Funcionalidades

- [x] **Busca por Código de Barras (EAN/UPC):** Consulta pontual de produtos por código com exibição de imagem, marca, quantidade, calorias e ingredientes.
- [x] **Busca e Filtros Dinâmicos por Categoria e País:**
  - Filtragem por qualidade nutricional (**Nutri-Score** A–E).
  - Filtragem por nível de processamento (**Grupo NOVA** 1–4).
  - Filtragem por tipos de alimentos e categorias.
  - Filtragem por restrições alimentares (**Sem Glúten** / **Sem Lactose**).
  - Consulta customizada por país (**Brasil, França, Espanha, etc.**).
- [x] **Interface Web Responsiva:** Front-end integrado exibindo os resultados dinamicamente em um **Grid centralizado** com tema dark (Preto e Verde Oliva).

---

## Tecnologias Utilizadas

### Back-end
- **Linguagem:** Java 17+
- **Framework:** Spring Boot 3.x (`spring-boot-starter-web`)
- **Arquitetura:** REST API / MVC
- **Serialização/Desserialização:** Jackson (`ObjectMapper`, Records/DTOs)
- **Cliente HTTP:** Java `HttpClient` / `HttpRequest`

### Front-end
- **HTML5:** Estruturação semântica e formulários dinâmicos.
- **CSS3:** Estilização com CSS Grid, Flexbox e paleta customizada (Preto, Verde Oliva `#556b2f` / `#6b8e23` e Branco).
- **JavaScript (Vanilla JS):** Manipulação da DOM e consumo da API REST via `fetch`.

---

## 💻 Arquitetura e Fluxo de Dados

O projeto reutiliza toda a lógica das classes de serviço (`ConsumoApi` e `convertedados`) sem alterar a camada do modelo. O novo `ControladorFoodWeb` substitui as entradas de terminal pelo protocolo HTTP.

```text
[ Front-end (HTML/CSS/JS) ] 
            │ 
            ▼  (Requisição HTTP / fetch)
[ ControladorFoodWeb (REST Controller) ] 
            │ 
            ▼  (Consome Serviços)
[ ConsumoApi & convertedados ] ──► [ API Open Food Facts ]
            │ 
            ▼  (Retorna DTOs serializados)
[ Front-end (Grid de Produtos) ]

🔌 Endpoints da API REST Local
A controller REST expõe as seguintes rotas na porta 8080:

Buscar por Código de Barras: GET /api/produtos/codigo/{codigo}

Buscar por Filtros / Categoria / País: GET /api/produtos/buscar?pais={pais}&nutriscore={grade}&nova={grupo}&termo={termo}&restricao={gluten|lactose}
```
📂 Estrutura do Projeto
```
Plaintext
src/main/
├── java/com/Retr0/MenuCerto/
│   │
│   ├── Controller/              # Controllers da Aplicação
│   │   ├── ControladorFoodWeb.java  # REST Controller (Spring Web)
│   │   └── ControladorFood.java     # Controller Antigo (Console/Terminal)
│   │
│   ├── Model/                   # Records/DTOs para desserialização
│   │   ├── ProductResponseDto.java
│   │   ├── ProductDTO.java
│   │   ├── ProdutoDto.java
│   │   ├── SearchResponseDto.java
│   │   └── NutrimentsDTO.java
│   │
│   ├── Service/                 # Lógica de consumo HTTP e conversão Jackson
│   │   ├── ConsumoApi.java
│   │   └── convertedados.java
│   │
│   └── MenuCertoApplication.java# Classe Principal (Spring Boot Application)
│
└── resources/
    └── static/                  # Arquivos Estáticos da Interface Web
        ├── index.html           # Estrutura da Página e Formulários
        ├── style.css            # Estilização Tema Dark/Verde Oliva e Grid
        └── script.js            # Integração JavaScript e Renderização
```
⚙️ Como Executar o Projeto
Clonar o Repositório:

Bash
```
git clone [https://github.com/seu-usuario/MenuCerto.git](https://github.com/seu-usuario/MenuCerto.git)
Executar a Aplicação Spring Boot:
Rode a classe MenuCertoApplication.java na sua IDE (IntelliJ IDEA, VS Code, Eclipse) ou via terminal:
```
Bash
```
./mvnw spring-boot:run
```
