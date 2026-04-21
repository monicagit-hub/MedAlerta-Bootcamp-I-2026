# 💊 MedAlerta — Bootcamp I 2026

<div align="center">
  <p><b>Projeto desenvolvido durante o Bootcamp I da Escola Politécnica/UNINTER — uma imersão prática de 3 dias para construção de uma solução real do zero ao produto.</b></p>
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

O **MedAlerta** é um sistema de gerenciamento de medicamentos desenvolvido para ajudar pessoas a manterem a regularidade no uso de medicamentos. O problema é real: diferentes horários, doses e períodos de tratamento tornam o esquecimento ou uso incorreto de medicamentos algo muito comum.

### O sistema permite:
- 👤 Cadastro de usuários
- 💊 Cadastro de medicamentos
- 🔗 Vínculo entre usuários e medicamentos com dosagem
- ⏰ Definição de horários de uso
- 🔔 Emissão e controle de alertas
- ✅ Confirmação e registro do consumo do medicamento

---

## 🗓️ Agenda do Bootcamp

| Dia | Data | Tema | Professor |
|-----|------|------|-----------|
| Dia 1 | 13/04/2026 | Arquitetura básica e segurança | Prof. Me. Guilherme Ditzel Patriota |
| Dia 2 | 14/04/2026 | Banco de Dados com MySQL | Profª. Drª. Neusa Grando |
| Dia 3 | 15/04/2026 | Programação + CRUD | Prof. Me. Rodrigo Nascimento |

---

## 🏗️ Arquitetura em Camadas

O projeto segue o padrão de **arquitetura em camadas** com responsabilidades bem definidas:

```
Console (App)
     ↓
Service (Regras de negócio)
     ↓
Repository (Acesso aos dados)
     ↓
Banco de Dados (MySQL)
```

| Camada | Responsabilidade |
|--------|-----------------|
| **App** | Interface do usuário via console, menus e leitura de dados |
| **Service** | Validações e regras de negócio |
| **Repository** | Acesso ao banco via JPA — CRUD pronto sem SQL manual |
| **Banco** | Persistência dos dados no MySQL |

---

## 🗄️ Modelagem do Banco de Dados

O modelo evoluiu ao longo do bootcamp. O modelo final conta com **6 tabelas** seguindo a cadeia de integridade referencial:

```
Usuario
  └── UsuarioMedicamento (vínculo N:M)
            └── Horario (quando tomar)
                    └── Alerta (disparo gerado)
                              └── Registro (confirmação do consumo)
Medicamento
  └── UsuarioMedicamento
```

### Tabelas

| Tabela | Descrição | Campos principais |
|--------|-----------|-------------------|
| `usuario` | Dados do usuário | id, nome, telefone, email, endereço |
| `medicamento` | Dados do medicamento | id, nomeComercial, nomeGenerico, quantidade, formaUso |
| `usuario_medicamento` | Vínculo entre usuário e medicamento | id, idUsuario, idMedicamento, dosagem, formaUso |
| `horario` | Horários de uso do medicamento | id, idUsuarioMedicamento, horarioUso, frequenciaUso |
| `alerta` | Alertas gerados por horário | id, idHorario, dataHorarioAlerta, statusAlerta |
| `registro` | Confirmação de consumo | id, idAlerta, dataHorarioConsumo, confirmacaoConsumo |

### Por que 6 tabelas?

O modelo simplificado (3 tabelas) não permitia que um usuário tomasse o mesmo medicamento em horários diferentes. O modelo completo resolve isso separando vínculo, horário, alerta e registro em tabelas independentes — permitindo múltiplos horários, múltiplos alertas e histórico completo de consumo.

---

## 📦 Estrutura do Projeto

```
src/main/java/br/uninter/medalerta/
├── MedalertaApplication.java
├── app/
│   └── App.java                        ← Menu console (CommandLineRunner)
├── model/
│   ├── Usuario.java                    ← @Entity
│   ├── Medicamento.java                ← @Entity + Enum quantidade
│   ├── UsuarioMedicamento.java         ← @Entity (vínculo N:M)
│   ├── Horario.java                    ← @Entity + LocalTime
│   ├── Alerta.java                     ← @Entity + Enum statusAlerta
│   └── Registro.java                   ← @Entity + @OneToOne
├── repository/
│   ├── UsuarioRepository.java          ← JpaRepository<Usuario, Integer>
│   ├── MedicamentoRepository.java
│   ├── UsuarioMedicamentoRepository.java
│   ├── HorarioRepository.java
│   ├── AlertaRepository.java
│   └── RegistroRepository.java
└── service/
    ├── UsuarioService.java             ← listar, buscar, salvar, deletar
    ├── MedicamentoService.java
    ├── UsuarioMedicamentoService.java
    ├── HorarioService.java
    ├── AlertaService.java
    └── RegistroService.java
```

---

## 🔗 Relacionamentos JPA

| Relacionamento | Entre | Descrição |
|---------------|-------|-----------|
| `@OneToMany` / `@ManyToOne` | Usuario ↔ UsuarioMedicamento | Um usuário tem vários vínculos |
| `@OneToMany` / `@ManyToOne` | Medicamento ↔ UsuarioMedicamento | Um medicamento tem vários vínculos |
| `@OneToMany` / `@ManyToOne` | UsuarioMedicamento ↔ Horario | Um vínculo tem vários horários |
| `@OneToMany` / `@ManyToOne` | Horario ↔ Alerta | Um horário gera vários alertas |
| `@OneToOne` | Alerta ↔ Registro | Um alerta tem no máximo um registro |

---

## 🧱 Infraestrutura

O projeto utiliza **Docker** para padronizar o ambiente, com dois containers:

- **app** — ambiente Java com Maven (desenvolvimento)
- **db** — MySQL 8.4

```
docker compose up --build
```

Características:
- Comunicação entre containers via rede interna `medalerta-net`
- Banco acessível externamente via Workbench (porta 3306)
- Variáveis de ambiente via `.env` — credenciais nunca versionadas
- Ambiente reproduzível em qualquer máquina

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.x | Framework |
| Spring Data JPA | 3.x | Persistência e CRUD |
| Hibernate | 6.x | ORM |
| MySQL | 8.4 | Banco de dados |
| Docker | - | Containerização |
| Docker Compose | - | Orquestração |
| Maven | 3.9 | Gerenciador de dependências |
| GitHub Actions | - | CI/CD |

---

## ⚙️ Pré-requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [VSCode](https://code.visualstudio.com/)
- [Git](https://git-scm.com/)
- Extensão **Dev Containers** no VSCode
- Extensão **Docker** no VSCode

---

## 🚀 Como executar o projeto

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd Projeto
```

### 2. Configurar variáveis de ambiente

```bash
cp .env.example .env
# edite o .env com suas configurações
```

### 3. Subir os containers

```bash
docker compose up --build
```

### 4. Rodar o projeto dentro do container

```bash
docker compose exec app mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 5. Acessos

| Serviço | Como acessar |
|---------|-------------|
| Aplicação | Terminal — menu interativo no console |
| MySQL | localhost:3306 (via MySQL Workbench) |

---

## 🔐 Segurança

- Variáveis de ambiente via `.env` — credenciais nunca no código
- Usuário MySQL com **menor privilégio** — acesso só ao banco `medalerta`
- Proteção contra SQL Injection via JPA/Hibernate com Prepared Statements
- `.gitignore` e `.dockerignore` configurados
- Separação clara de responsabilidades entre camadas

---

## 🧪 CI/CD — GitHub Actions

O projeto inclui pipeline de integração contínua que:
- Sobe um MySQL temporário
- Executa os testes automatizados
- Gera a imagem Docker de produção

---

## 👨‍🏫 Professores

| [<img src="https://avatars.githubusercontent.com/u/8054061?v=4" width="75px;"/>](https://github.com/nascimentoRodrigo) | [<img src="https://avatars3.githubusercontent.com/u/60905310?s=460&v=4" width="75px;"/>](https://github.com/guipatriota) | [<img src="https://media.licdn.com/dms/image/v2/C4D03AQEZ0ucDyC-I3Q/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1656806969197?e=1777507200&v=beta&t=s6pQJeDpx0wPD0s16Efp6SX4-CDDFdq9ucW1nEtoucY" width="75px;"/>](https://github.com/neusagrando) | [<img src="https://avatars.githubusercontent.com/u/5274908?v=4" width="75px;"/>](https://github.com/jadinhu) |
|:---:|:---:|:---:|:---:|
| [Prof. Me. Rodrigo Nascimento](https://github.com/nascimentoRodrigo) | [Prof. Me. Guilherme Patriota](https://github.com/guipatriota) | [Prof. PhD. Neusa Grando](https://github.com/neusagrando) | [Prof. Me. Jadson Almeida](https://github.com/jadinhu) |

---

## 🏫 Instituição

**Escola Politécnica — UNINTER**

---

## 🚀 Evolução do Projeto

Este projeto foi evoluído além do bootcamp com boas práticas de mercado.

> Acesse o [documento de evolução](./Projeto/docs/EVOLUCAO.md) para ver os novos passos!
