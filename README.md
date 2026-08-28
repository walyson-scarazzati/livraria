# Livraria

Sistema de gerenciamento de livraria (CRUD de livros e usuários), composto por uma API REST em
Spring Boot e um front-end em Angular.

## Estrutura do projeto

```
livraria/
├── livraria-api/   # Back-end: Spring Boot 2.0.5 (Java 8) + MySQL
└── livraria-ui/     # Front-end: Angular 8
```

## Tecnologias

**Back-end (`livraria-api`)**
- Java 8
- Spring Boot 2.0.5 (Web, Data JPA, Actuator, DevTools)
- MySQL (via `mysql-connector-java`)
- Swagger / Springfox (documentação da API)
- JasperReports (geração de PDF de livros/usuários)
- ModelMapper, Lombok
- Maven

**Front-end (`livraria-ui`)**
- Angular 8 (CLI 8.0.6)
- Angular Material, Bootstrap 4, ngx-bootstrap
- RxJS

## Pré-requisitos

- Java 8 (JDK)
- Maven (ou usar o `mvnw` incluso no projeto)
- Node.js e npm
- Angular CLI (`npm install -g @angular/cli`) — opcional, o projeto já traz `@angular/cli` como devDependency
- MySQL 8 rodando em `localhost:3306` (ou um container Docker equivalente)

## Configuração do banco de dados

As credenciais usadas pela API estão em
[`livraria-api/src/main/resources/application.properties`](livraria-api/src/main/resources/application.properties):

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/livraria?useSSL=false&createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC
spring.datasource.username = root
spring.datasource.password = 123456
```

O banco `livraria` é criado automaticamente (`createDatabaseIfNotExist=true`) e as tabelas são
atualizadas automaticamente pelo Hibernate (`spring.jpa.hibernate.ddl-auto = update`).

### Subindo o MySQL com Docker

Se não tiver um MySQL local, é possível subir um container com as mesmas credenciais:

```bash
docker run -d --name livraria-mysql \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=livraria \
  -p 3306:3306 \
  mysql:8.0 --default-authentication-plugin=mysql_native_password
```

## Rodando o back-end (`livraria-api`)

```bash
cd livraria-api
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080/livraria-api`.

- Swagger UI: `http://localhost:8080/livraria-api/swagger-ui.html`
- Actuator: `http://localhost:8080/livraria-api/actuator`

> **Nota:** o projeto usa Spring Boot 2.0.5 (2018), compilado para Java 8. Recomenda-se compilar e
> rodar com um JDK 8 mesmo que outras versões do Java estejam instaladas na máquina.

## Rodando o front-end (`livraria-ui`)

```bash
cd livraria-ui
npm install
npm start   # equivalente a `ng serve`
```

A aplicação sobe em `http://localhost:4200` e consome a API configurada em
[`src/environments/environment.ts`](livraria-ui/src/environments/environment.ts)
(`http://localhost:8080/livraria-api` por padrão).

## Testes

- Back-end: `cd livraria-api && ./mvnw test`
- Front-end: `cd livraria-ui && npm test`

## Build de produção

- Back-end: `cd livraria-api && ./mvnw clean package` (gera um `.war` em `target/`)
- Front-end: `cd livraria-ui && npm run build -- --prod` (gera os artefatos em `dist/`)
