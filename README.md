Literalura

API REST para gerenciamento de autores e livros, com integração à API pública Gutendex para busca de livros. Projeto desenvolvido em Java 17 com Spring Boot 3, utilizando PostgreSQL para persistência e Jackson para manipulação de JSON.

Descrição do Projeto
Literalura é uma aplicação backend que permite buscar e gerenciar informações sobre livros e autores. Ela realiza requisições HTTP à API Gutendex para buscar livros por título, armazena os dados localmente em um banco PostgreSQL, e oferece funcionalidades para consultar autores, livros e estatísticas via console.

Tecnologias Utilizadas
Java 17+

Spring Boot 3.2.3

Maven 4+

Spring Data JPA (Hibernate)

PostgreSQL

Jackson (2.16) para mapeamento JSON

API Gutendex (https://gutendex.com/books)

Funcionalidades Principais
Buscar livros por título consumindo a API Gutendex

Listar todos os livros buscados e armazenados localmente

Listar autores dos livros cadastrados

Listar autores vivos em determinado ano

Exibir estatísticas da quantidade de livros por idioma (mínimo 2 idiomas)

Persistência de dados com relacionamento entre Livro e Autor no PostgreSQL

Interface de interação via console com menu de opções

Tratamento básico de erros e código limpo

Estrutura do Projeto
graphql
Copiar
Editar
src/main/java/com/literalura/app/
│
├── app/              # Classe principal e menu interativo via console
├── config/           # Configurações do projeto e banco de dados
├── controller/       # (Se aplicável) Controllers REST para APIs
├── dto/              # Data Transfer Objects (DTOs) para API e comunicação
├── exception/        # Tratamento global de exceções personalizadas
├── model/            # Entidades JPA: Livro, Autor, Idioma (enum)
├── repository/       # Interfaces JpaRepository para acesso ao banco
├── service/          # Lógica de negócio e integração com API externa
└── util/             # Utilitários como cliente HTTP para Gutendex
Requisitos Obrigatórios Atendidos
Uso de Java 17+ e Spring Boot 3.2.3

Consumo da API Gutendex via HttpClient, HttpRequest e HttpResponse

Mapeamento JSON com Jackson (@JsonAlias, @JsonIgnoreProperties)

Persistência com Spring Data JPA e PostgreSQL

Entidades Livro e Autor com relacionamento JPA

Menu interativo implementado via console (Scanner + CommandLineRunner)

Código limpo e organizado, com tratamento básico de erros

Como Executar
Pré-requisitos
Java 17 instalado

Maven 4 instalado

PostgreSQL instalado e rodando (porta padrão 5432)

Banco de dados criado: literalura

Usuário do banco: admin com senha 123

Passos para execução
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/seu-usuario/literalura.git
cd literalura
Configure o banco de dados no arquivo src/main/resources/application.properties:

properties
Copiar
Editar
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=admin
spring.datasource.password=123
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
Compile e execute a aplicação:

bash
Copiar
Editar
mvn clean install
mvn spring-boot:run
Interaja com o menu no console conforme as opções apresentadas.

Menu Interativo
Ao iniciar, o programa exibirá um menu com as opções:

Buscar livro por título

Listar todos os livros buscados

Listar autores

Listar autores vivos em determinado ano

Exibir quantidade de livros por idioma

Sair

Exemplo de Uso
Após iniciar a aplicação, escolha a opção desejada no menu para realizar buscas, listagens ou estatísticas.

Tratamento de Erros
Retorna mensagens amigáveis para erros de validação e entradas inválidas

Retorna erro quando recurso não encontrado (ex: livro ou autor)

Tratamento de erros internos para garantir estabilidade

Considerações Finais
Este projeto demonstra integração com APIs externas, manipulação de JSON, persistência relacional e boas práticas de organização em Java Spring Boot. Pode ser expandido com:

Interface web (frontend)

Endpoints RESTful para consumo via HTTP

Relatórios e filtros adicionais

Testes unitários e integração

Documentação via OpenAPI (Swagger)


