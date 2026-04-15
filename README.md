# 💊 MedAlerta — Bootcamp I 2026

<div align="center">
  <p><b>Projeto desenvolvido durante o Bootcamp I da Escola Politécnica/UNINTER, com o tema "Do zero ao produto" — uma imersão prática de 3 dias para construção de uma solução real.</b></p>
</div>

<p align="center">
  <a href="https://www.java.com/" title="Java">
    <img src="https://github.com/get-icon/geticon/raw/master/icons/java.svg" alt="Java" height="21px">
  </a>
  +
  <a href="https://spring.io/projects/spring-boot" title="Spring Boot">
    <img src="https://img.icons8.com/color/48/spring-logo.png" alt="Spring Boot" height="21px">
  </a>
  +
  <a href="https://www.mysql.com/" title="MySQL">
    <img src="https://github.com/get-icon/geticon/raw/master/icons/mysql.svg" alt="MySQL" height="21px">
  </a>
  +
  <a href="https://www.docker.com/" title="Docker">
    <img src="https://github.com/get-icon/geticon/raw/master/icons/docker-icon.svg" alt="Docker" height="21px">
  </a>
  +
  <a href="https://code.visualstudio.com/" title="VSCode">
    <img src="https://github.com/get-icon/geticon/raw/master/icons/visual-studio-code.svg" alt="VSCode" height="21px">
  </a>
</p>

---

## 📋 Sobre o Projeto

O **MedAlerta** é um aplicativo simples, funcional e acessível criado para ajudar pessoas a manterem a regularidade no uso de medicamentos. O problema é real: diferentes horários, doses e períodos de tratamento tornam o esquecimento ou uso incorreto de medicamentos algo muito comum.

### O sistema permite:
- 📌 Cadastro de medicações
- ⏰ Definição de horários de uso
- 🔔 Emissão de alertas
- ✅ Confirmação do consumo do medicamento

---

## 🗓️ Agenda do Bootcamp

| Dia | Data | Tema |
|-----|------|------|
| Dia 1 | 13/04/2026 | Arquitetura básica e segurança |
| Dia 2 | 14/04/2026 | Banco de Dados com MySQL |
| Dia 3 | 15/04/2026 | Programação + CRUD |

---

## 🏗️ Arquitetura

O projeto segue o padrão de **arquitetura em camadas**:

```
Service → Repository → Banco de Dados
```

- **Service** — regras de negócio
- **Repository (CRUD)** — acesso aos dados
- **Banco (MySQL)** — persistência

---

## 🧱 Infraestrutura

O projeto utiliza **Docker** para padronizar o ambiente, com dois containers:

- **app** — ambiente Java (desenvolvimento)
- **db** — MySQL 8.4

Características:
- Comunicação entre containers via rede interna
- Banco acessível externamente via Workbench (porta 3306)
- Aplicação acessível via navegador (porta 8080)
- Ambiente reproduzível em qualquer máquina

---

## 🛠️ Tecnologias Utilizadas

- **Java + Spring Boot** — linguagem e framework principal
- **MySQL** — banco de dados relacional
- **Docker + Docker Compose** — containerização e orquestração
- **DevContainer + VSCode** — ambiente de desenvolvimento padronizado
- **GitHub Actions** — CI/CD automatizado
- **Maven** — gerenciador de dependências

---

## ⚙️ Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [VSCode](https://code.visualstudio.com/)
- [Git](https://git-scm.com/)
- Extensão **Dev Containers** no VSCode
- Extensão **Docker** no VSCode

---

## 🚀 Como executar o projeto

### 1. Configurar variáveis de ambiente

```bash
cp .env.example .env
# edite o .env com suas configurações
```

### 2. Subir os containers

```bash
docker compose up --build
```

### 3. Rodar o projeto Java

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 4. Acessos

| Serviço | URL |
|---------|-----|
| Aplicação | http://localhost:8080 |
| Health check | http://localhost:8080/actuator/health |
| MySQL | localhost:3306 |

---

## 🗄️ Banco de Dados

O banco MySQL é inicializado automaticamente através dos scripts em `docker/mysql/init/`.

> ⚠️ Os scripts `.sql` são executados **apenas na primeira inicialização**. Para reinicializar o banco:

```bash
docker compose down -v
docker compose up --build
```

---

## 🧪 CI/CD — GitHub Actions

O projeto inclui um pipeline de integração contínua que:

- Sobe um MySQL temporário
- Executa os testes automatizados
- Gera a imagem Docker de produção

---

## 🔐 Segurança

A estrutura já considera boas práticas iniciais:

- Uso de variáveis de ambiente para credenciais (`.env`)
- Separação entre configuração e código
- Proteção contra SQL Injection via ORM (próximas etapas)
- `.gitignore` e `.dockerignore` configurados

---

## 📁 Estrutura do Repositório

```
medalerta/
├── .devcontainer/
│   └── devcontainer.json       # Configuração do ambiente de desenvolvimento
├── .github/
│   └── workflows/
│       └── ci.yml              # Pipeline CI/CD
├── docker/
│   └── mysql/
│       └── init/
│           └── 01-init.sql     # Script de inicialização do banco
├── docs/
│   └── project/
│       └── diagrams/           # Diagramas UML do projeto
├── src/
│   └── main/
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-prod.yml
│           └── application-test.yml
├── Aulas/                      # Anotações e READMEs por dia de aula
├── .dockerignore
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## 🎯 Objetivos de Aprendizagem

- Desenvolver competências técnicas fundamentais
- Aproximar teoria e prática
- Construir uma solução progressiva ao longo dos 3 dias
- Vivenciar uma experiência semelhante ao mercado de trabalho

---

## 👨‍🏫 Professores

| [<img src="https://avatars.githubusercontent.com/u/8054061?v=4" width="75px;"/>](https://github.com/nascimentoRodrigo) | [<img src="https://avatars3.githubusercontent.com/u/60905310?s=460&v=4" width="75px;"/>](https://github.com/guipatriota) | [<img src="https://media.licdn.com/dms/image/v2/C4D03AQEZ0ucDyC-I3Q/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1656806969197?e=1777507200&v=beta&t=s6pQJeDpx0wPD0s16Efp6SX4-CDDFdq9ucW1nEtoucY" width="75px;"/>](https://github.com/neusagrando) | [<img src="https://avatars.githubusercontent.com/u/5274908?v=4" width="75px;"/>](https://github.com/jadinhu) |
|:---:|:---:|:---:|:---:|
| [Prof. Me. Rodrigo Nascimento](https://github.com/nascimentoRodrigo) | [Prof. Me. Guilherme Patriota](https://github.com/guipatriota) | [Prof. PhD. Neusa Grando](https://github.com/neusagrando) | [Prof. Me. Jadson Almeida](https://github.com/jadinhu) |

---

## 🏫 Instituição

**Escola Politécnica — UNINTER**

---

## 📜 Certificação

Este bootcamp oferece **certificação de 8 horas** ao concluinte.

---

*Bootcamp I — 2026 | Escola Politécnica/UNINTER*