# Votação

## Objetivo

Este projeto é um back-end em Java Spring Boot para um aplicativo de votação.
Possui as funcionalidades de criação de pautas, abertura de sessões de votação e registro de votos.


### Tecnologias Utilizadas

- Java 17
- Spring Boot
- MongoDB
- Spring Web
- Spring Data MongoDB
- Lombok
- Swagger/OpenAPI

### Como Executar o Projeto

- Pré-requisitos
  - Java 17
  - MongoDB 6.0.1 instalado e rodando

Como default Como o projeto utiliza porta 8080 para o serviço back-end e a porta 27017 para o banco de dados.
Caso seja necessário alterar essas portas, edite o arquivo `src/main/resources/application.properties`: 

```
  server.port=8080
  spring.data.mongodb.uri=mongodb://localhost:27017/topics_db
```


### Documentação

A documentação detalhada da API está disponível via Swagger.
Para acessar o swagger basta acessar o endpoint /swagger-ui/index.html, nele contém detalhadamente os contratos das apis incluindo métodos, parâmetros e exemplos de requisição/resposta.

## Funcionalidades

### Gerenciamento de Pautas

- Cadastro de pauta
  - É possível cadastrar uma pauta para que possa ser submetida a uma votação.
  - O usuário pode definir o nome e a descrição da pauta.


- Listar todas as pautas


- Consultar o resultado de uma votação
  - É possível consultar o resultado de uma votação encerrada.
  - O usuário pode buscar o resultado informando o ID da pauta. 
  - Deverá trazer sempre a última sessão finalizada.


- Consultar o histórico de sessões de uma pauta
  - Lista todas as sessões de votação já realizadas e encerradas de uma pauta,

> Endpoints Pautas

| Método | Endpoint                          | Descrição                                      |
|--------|-----------------------------------|------------------------------------------------|
| POST   | `/api/v1/topics/create`          | Criação de nova pauta                          |
| GET    | `/api/v1/topics`                 | Listar todas as pautas (paginado)             |
| GET    | `/api/v1/topics/result/{id}`     | Buscar resultado da votação por pauta         |
| GET    | `/api/v1/topics/history/{id}`    | Listar histórico de votações da pauta (paginado) |


### Gerenciamento de Sessões de votação

- Abrir uma sessão de votação para uma pauta
  - O usuário poderá definir o tempo que ficará aberta para votação, ou ficará aberta por 1 minuto como padrão. 
  - É possível editar o tempo padrão de abertura da pauta edite o arquivo `src/main/resources/application.properties`:
    ```
      voting.session.default.duration=1
    ```
  - A sessão ficará aberta para votos até o tempo expirar ou até que o usuário a encerre manualmente.
  - Se pauta já possuir uma sessão em aberto, não deverá ser possível abrir uma sessão de votação e deverá retornar uma mensagem.


- Listar sessões de votação abertas
  - Visualizar todas as sessões de votação que estão abertas, para que o usuário possa escolher em qual delas votar. 
  - Se não houver sessões abertas, o sistema deve retornar uma mensagem adequada.


- Votar em uma sessão de votação
  - O associado deve fornecer seu CPF.
    - O cpf é validado de forma randomica, dessa forma, um mesmo cpf pode vir `ABLE_TO_VOTE` ou `UNABLE_TO_VOTE`
  - O voto pode ser apenas `SIM` ou `NAO`.
  - O associado pode votar apenas uma vez por pauta.

> Endpoints - Sessão de Votação

| Método | Endpoint                                | Descrição                                          |
|--------|-----------------------------------------|----------------------------------------------------|
| POST   | `/api/v1/voting/sessions/vote`         | Registra o voto de um associado na sessão indicada |
| GET    | `/api/v1/voting/sessions/open`         | Lista as sessões de votação abertas               |
| POST   | `/api/v1/voting/sessions/open`         | Abre uma sessão de votação para uma pauta        |
| POST   | `/api/v1/voting/sessions/close/{id}`   | Fecha a sessão de votação antes do tempo indicado |


### Teste Unitário

- Implementado teste unitário para validação da abertura de uma sessão de votação.


### Logs

- Adicionado logs informativos nos controllers para identicar a entrada no endpoint.

