# Literalura - Catálogo de Livros 📚

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![Frontend](https://img.shields.io/badge/Frontend-HTML/CSS/JS-yellow.svg)
![Status](https://img.shields.io/badge/Status-Concluído-success)

### 📖 Descrição do Projeto

**Literalura** é uma aplicação Full-Stack desenvolvida como parte do desafio de programação da Alura. O projeto funciona como um catálogo de livros interativo, permitindo que os usuários busquem por livros através da API pública [Gutendex](https://gutendex.com/), salvem os resultados em um banco de dados local e realizem diversas consultas sobre os dados armazenados.

A aplicação é dividida em duas partes principais:
-   **Backend:** Uma API REST robusta construída com **Java** e **Spring Boot**, responsável por toda a lógica de negócio, comunicação com a API externa e persistência de dados em um banco PostgreSQL.
-   **Frontend:** Uma interface de usuário moderna e responsiva, criada com **HTML, CSS e JavaScript puros**, que consome a API do backend para proporcionar uma experiência dinâmica e agradável.

---


### 🚀 Funcionalidades

A interface do Literalura permite realizar as seguintes operações:

-   **Buscar Livro por Título:** Pesquisa um livro na API Gutendex e salva o resultado no banco de dados local.
-   **Listar Livros Registrados:** Exibe todos os livros que já foram salvos no banco.
-   **Listar Autores Registrados:** Exibe todos os autores dos livros salvos.
-   **Listar Autores Vivos em Determinado Ano:** Filtra e exibe autores que estavam vivos em um ano específico.
-   **Listar Livros por Idioma:** Permite buscar livros disponíveis em um idioma específico (ex: `pt`, `en`, `es`, `fr`).

---

### 💻 Tecnologias Utilizadas

Este projeto foi construído com as seguintes tecnologias:

#### **Backend**
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA:** Para persistência de dados de forma simplificada.
* **PostgreSQL:** Banco de dados relacional para armazenar os livros e autores.
* **Jackson:** Para manipulação e conversão de JSON.
* **Maven:** Para gerenciamento de dependências do projeto.

#### **Frontend**
* **HTML5:** Para a estrutura da página.
* **CSS3:** Para estilização, com uso de variáveis, Flexbox e um design moderno.
* **JavaScript (ES6+):** Para toda a lógica de interatividade e consumo da API (usando `fetch` assíncrono).
* **Bootstrap 5:** Para componentes de UI e layout responsivo.

---

### ⚙️ Instalação e Execução

Para executar este projeto localmente, siga os passos abaixo.

#### **Pré-requisitos**

* **Java 17** ou superior instalado.
* **Maven 3.8** ou superior instalado.
* **PostgreSQL 15** ou superior instalado e rodando.
* Uma **IDE** de sua preferência (ex: IntelliJ IDEA, VS Code, Eclipse).
* Um navegador web moderno (ex: Google Chrome, Firefox).

#### **1. Configuração do Backend**

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/literalura.git](https://github.com/seu-usuario/literalura.git)
    cd literalura/backend # Navegue para a pasta do backend
    ```

2.  **Configure o Banco de Dados:**
    * Crie um banco de dados no PostgreSQL chamado `literalura`.
    * Abra o arquivo `src/main/resources/application.properties`.
    * Atualize as propriedades de conexão com suas credenciais do PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
        spring.datasource.username=SEU_USUARIO_POSTGRES
        spring.datasource.password=SUA_SENHA_POSTGRES
        spring.jpa.hibernate.ddl-auto=update
        ```

3.  **Compile e Execute a Aplicação:**
    * **Pelo Maven:**
        ```bash
        mvn clean install
        java -jar target/literalura-0.0.1-SNAPSHOT.jar
        ```
    * **Pela sua IDE:**
        Abra o projeto na sua IDE e execute a classe principal que contém o método `main` e a anotação `@SpringBootApplication`.

O backend estará rodando e pronto para receber requisições em `http://localhost:8080/api`.

#### **2. Execução do Frontend**

1.  **Abra o arquivo `index.html`:**
    * A maneira mais simples é abrir o arquivo `index.html` diretamente no seu navegador.

A interface do Literalura estará pronta para ser usada!

---

### 🌐 Endpoints da API

O backend expõe os seguintes endpoints, todos sob o prefixo `/api`:

| Método HTTP | Endpoint                         | Descrição                                         |
| :---------- | :------------------------------- | :-------------------------------------------------- |
| `GET`       | `/livros`                        | Lista todos os livros registrados.                  |
| `GET`       | `/livros/buscar`                 | Busca um livro por título. (Ex: `?titulo=Dom+Casmurro`) |
| `GET`       | `/livros/top10`                  | Lista os 10 livros mais baixados.                   |
| `GET`       | `/livros/idioma/{idioma}`        | Lista livros por um idioma específico. (Ex: `/pt`)   |
| `GET`       | `/autores`                       | Lista todos os autores registrados.                 |
| `GET`       | `/autores/vivos-em/{ano}`        | Lista autores vivos em um determinado ano.          |
| `GET`       | `/autores/buscar`                | Busca um autor por nome. (Ex: `?nome=Machado+de+Assis`)|

---

### 👨‍💻 Autor

Projeto desenvolvido por **Camila F. Prado**.

---

### 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
