# 🚀 WorkHub - API RESTful de Gestão Colaborativa de Workspaces

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green?style=for-the-badge&logo=springsecurity)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-4169E1?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)

O **WorkHub** é uma API RESTful robusta desenvolvida em Java e Spring Boot para gerenciamento colaborativo de equipes e espaços de trabalho (workspaces). A aplicação oferece autenticação *stateless* com JWT, controle de acesso refinado baseado em perfis (RBAC), gestão de membros e convites, e isolamento de ambientes com Docker.

---

## 📌 Sumário

- [Visão Geral](#-visão-geral)
- [Tecnologias Utilizadas](#️-tecnologias-utilizadas)
- [Arquitetura e Estrutura do Projeto](#-arquitetura-e-estrutura-do-projeto)
- [Funcionalidades Principais](#️-funcionalidades-principais)
- [Documentação da API (Endpoints)](#-documentação-da-api-endpoints)
- [Como Executar o Projeto](#-como-executar-o-projeto)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Autor](#-autor)

---

## 🎯 Visão Geral

A proposta do WorkHub é resolver a complexidade de gerenciamento de permissões e colaboração em equipes de desenvolvimento ou projetos empresariais. A API implementa padrões modernos de arquitetura limpa, tratamento global de exceções, validação de dados via DTOs e segurança por tokens JWT de curta e média duração.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework Principal:** Spring Boot 3.x
- **Segurança & Autenticação:** Spring Security, JSON Web Token (JWT), BCrypt Password Encoder
- **Persistência & Banco de Dados:** Spring Data JPA, Hibernate, PostgreSQL, H2 Database (para testes)
- **Documentação Interativa:** Springdoc OpenAPI / Swagger UI
- **Utilitários & Produtividade:** Lombok, Bean Validation (Jakarta Validation)
- **Gerenciador de Dependências:** Apache Maven
- **Conteinerização:** Docker & Docker Compose

---

## 📂 Arquitetura e Estrutura do Projeto

O projeto segue a arquitetura em camadas (Controller - Service - Repository - Domain), visando alta coesão e baixo acoplamento:

```text
src/main/java/com/workhub/api
├── config/              # Configurações globais (Swagger, CORS, Beans)
├── controller/          # Endpoints REST (Recepção de requisições e respostas HTTP)
├── dto/                 # Data Transfer Objects (Request e Response payloads)
├── entity/              # Entidades JPA (Mapeamento ORM do banco de dados)
├── enums/               # Enumerações (Roles, Status de Convite, etc.)
├── exception/           # Handler de exceções global (@RestControllerAdvice)
├── repository/          # Interfaces de acesso ao banco (Spring Data JPA)
├── security/            # Filtros JWT, UserDetailsService e configurações de segurança
└── service/             # Regras de negócio da aplicação
```

---

## ⚙️ Funcionalidades Principais

### 🔐 Autenticação & Autorização (JWT)
- Cadastro de novos usuários com senha criptografada em BCrypt.
- Login com emissão de Token Bearer JWT.
- Extração do usuário autenticado no contexto via `@AuthenticationPrincipal`.

### 🏢 Gestão de Workspaces
- Criação, listagem, atualização e remoção de workspaces.
- Definição de proprietário (Owner) e níveis de permissão.

### 👥 Gestão de Membros & Convites (RBAC)
- Envio e aceite de convites para participação em workspaces.
- Atribuição de papéis: `ADMIN`, `MEMBER`, `GUEST`.
- Proteção contra acessos não autorizados entre workspaces distintos (IDOR prevention).

### 🛡️ Tratamento de Exceções & Validação
- Respostas padronizadas de erro (RFC 7807 / `ProblemDetail`).
- Validação estrita de entrada via `@Valid` e mensagens customizadas.

---

## 📑 Documentação da API (Endpoints)

Após iniciar a aplicação, a documentação interativa estará disponível em:

👉 `http://localhost:8080/swagger-ui.html`

### Principais Rotas

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|---------------|
| `POST` | `/api/v1/auth/register` | Cadastrar novo usuário | Pública |
| `POST` | `/api/v1/auth/login` | Realizar login e obter JWT | Pública |
| `GET` | `/api/v1/workspaces` | Listar workspaces do usuário | 🔒 JWT |
| `POST` | `/api/v1/workspaces` | Criar novo workspace | 🔒 JWT |
| `GET` | `/api/v1/workspaces/{id}` | Buscar detalhes do workspace | 🔒 JWT |
| `PUT` | `/api/v1/workspaces/{id}` | Atualizar dados do workspace | 🔒 JWT (Admin) |
| `DELETE` | `/api/v1/workspaces/{id}` | Remover workspace | 🔒 JWT (Owner) |
| `POST` | `/api/v1/workspaces/{id}/invites` | Convidar membro para workspace | 🔒 JWT (Admin) |
| `PATCH` | `/api/v1/invites/{id}/accept` | Aceitar convite para workspace | 🔒 JWT |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17+ instalado
- Maven 3.8+ instalado
- Docker & Docker Compose (opcional, mas recomendado)

### 1. Clonar o repositório

```bash
git clone https://github.com/Ccamposhe/workhub.git
cd workhub
```

### 2. Executar via Docker Compose (Recomendado)

A maneira mais simples de subir a aplicação e o banco PostgreSQL é utilizando o Docker Compose:

```bash
docker-compose up -d
```

A API estará rodando em `http://localhost:8080`.

### 3. Executar Localmente (Sem Docker para a API)

Caso queira rodar apenas o PostgreSQL no Docker:

```bash
# Iniciar o banco PostgreSQL via container
docker run --name workhub-db -e POSTGRES_DB=workhub -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15-alpine

# Compilar e rodar a aplicação Spring Boot
./mvnw clean package
./mvnw spring-boot:run
```

---

## 🔑 Variáveis de Ambiente

As configurações principais podem ser ajustadas no arquivo `src/main/resources/application.yml` ou via variáveis de ambiente:

| Variável | Valor Padrão | Descrição |
|----------|---------------|-----------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/workhub` | URL do banco PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Usuário do banco de dados |
| `SPRING_DATASOURCE_PASSWORD` | `postgres` | Senha do banco de dados |
| `JWT_SECRET` | *(Chave secreta de 256 bits)* | Chave de assinatura dos tokens JWT |
| `JWT_EXPIRATION_MS` | `86400000` (24h) | Tempo de expiração do token em ms |

---

## 👤 Autor

Desenvolvido por **Carlos Henrique Campos**.

💼 LinkedIn: [Carlos Henrique Campos](#)
💻 GitHub: [@Ccamposhe](https://github.com/Ccamposhe)
