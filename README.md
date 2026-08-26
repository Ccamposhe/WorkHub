# 🚀 WorkHub - API RESTful de Gestão Colaborativa de Workspaces

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green?style=for-the-badge&logo=springsecurity)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16%2B-4169E1?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)](LICENSE)
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow?style=for-the-badge)

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

- **Linguagem:** Java 21+
- **Framework Principal:** Spring Boot 4.x
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
├── config/          # Configurações gerais da aplicação e beans do Spring
├── controllers/     # Controladores REST e exposição dos endpoints HTTP
├── domain/          # Modelos de domínio, entidades JPA e regras core
│   └── enums/       # Enumerações do sistema (Roles, Status, etc.)
├── dtos/            # Data Transfer Objects para transporte de dados
├── infra/           # Infraestrutura, filtros de segurança (JWT) e tratamento de exceções
├── repositories/    # Interfaces de comunicação com o banco de dados (Spring Data JPA)
└── services/        # Camada de serviços e regras de negócio da aplicação
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
- Atribuição de papéis: `ADMIN`, `MEMBER`.
- Proteção contra acessos não autorizados entre workspaces distintos (IDOR prevention).

### 🛡️ Tratamento de Exceções & Validação
- Respostas padronizadas de erro (RFC 7807 / `ProblemDetail`).
- Validação estrita de entrada via `@Valid` e mensagens customizadas.

---

## 📑 Documentação da API (Endpoints)

Após iniciar a aplicação, a documentação interativa estará disponível em:

👉 `http://localhost:8080/swagger-ui.html`

### Principais Rotas da API

#### 🔐 Autenticação
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|---------------|
| `POST` | `/api/auth/login` | Autenticar usuário e obter token JWT | Pública |

#### 👤 Usuários
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|---------------|
| `POST` | `/api/users` | Cadastrar novo usuário | Pública |
| `GET` | `/api/users/me` | Obter perfil do usuário autenticado | 🔒 JWT |

#### 📁 Workspace
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|---------------|
| `POST` | `/api/workspaces` | Criar um novo workspace | 🔒 JWT |
| `GET` | `/api/workspaces` | Listar workspaces | 🔒 JWT |
| `GET` | `/api/workspaces/{workspaceId}/members` | Listar membros de um workspace | 🔒 JWT |

#### 👥 Membros do Workspace
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|---------------|
| `POST` | `/api/members/join` | Solicitar entrada em um workspace | 🔒 JWT |
| `GET` | `/api/members/my-workspaces` | Listar meus workspaces associados | 🔒 JWT |
| `PUT` | `/api/members/{memberId}/approve` | Aprovar entrada de um membro | 🔒 JWT |
| `DELETE` | `/api/members/{memberId}` | Remover membro do workspace | 🔒 JWT |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 21+ instalado
- Maven 3.8+ instalado
- Docker & Docker Compose (Recomendado)

### 1. Clonar o repositório

```bash
git clone https://github.com/Ccamposhe/workhub.git
cd workhub
```

### 2. Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto com:

```env
DB_USER=postgres
DB_PASSWORD=postgres
DB_NAME=workhub
```

### 3. Subir o banco de dados com Docker Compose

```bash
docker compose up -d
```

O PostgreSQL estará disponível em `localhost:5433`.

### 4. Compilar e rodar a aplicação Spring Boot

```bash
./mvnw clean package
./mvnw spring-boot:run
```

A API estará rodando em `http://localhost:8080`.
---

## 👤 Autor

Desenvolvido por **Carlos Henrique Campos**.

💼 LinkedIn: [Ccamposhe](https://www.linkedin.com/in/ccamposhe/)
💻 GitHub: [@Ccamposhe](https://github.com/Ccamposhe)
