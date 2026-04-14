# 📅 Dia 1 — 13/04/2026 | Arquitetura básica e segurança

---

## 🎯 Objetivos da aula

- Entender o problema proposto
- Apresentar a base do projeto
- Iniciar a construção do CRUD
- Mostrar como o produto final será evoluído nos próximos encontros

---

## ⚙️ Requisitos não funcionais

**Base profissional e reproduzível** — App Java + MySQL em containers, com uso de DevContainer + VSCode e CI via GitHub Actions, funcionando em qualquer máquina sempre no mesmo ambiente e versões.

**Escalabilidade** — Crescimento futuro garantido por separação de camadas e infra preparada.

**Segurança** — Dados protegidos, acesso controlado e proteção contra vulnerabilidades no banco de dados.

**Manutenibilidade** — Código organizado, fácil evolução, fácil mudança de provedor de nuvem e separação clara de responsabilidades.

---

## 🏗️ Arquitetura

O projeto adota arquitetura em camadas com responsabilidades claras:

```
Cliente → Serviço → Repositório → Banco de Dados
```

### Por que essa arquitetura?
- Baixo acoplamento
- Testável
- Evolutiva

---

## 🐳 Infraestrutura

A infraestrutura é baseada em **containers Docker** com Docker Compose, garantindo isolamento, portabilidade e reprodutibilidade em qualquer máquina.

---

## 🔐 Segurança

### No código
- Não confiar no input do cliente
- Princípio do menor privilégio
- Separação de responsabilidades

### No banco de dados
- Prevenção de SQL Injection com JPA/Hibernate e Prepared Statements
- CPF não é chave primária
- Não expor dados sensíveis

### Nas configurações
- Variáveis de ambiente via `.env`
- Uso de secrets
- ⚠️ **Nunca versionar credenciais!**

### No repositório
- `.gitignore` configurado
- `.dockerignore` configurado
- Evitar vazamentos de dados sensíveis

---

## 🗂️ Controle do projeto e padrões de desenvolvimento

### Controle via GitHub
- GitHub Projects para organização
- Issues para rastreamento de tarefas

### Padrões de commit e branch
- Commits curtos e objetivos
- Uma branch por tarefa
- Padronização de nomes de branches e mensagens de commit

### Pull Request
- Revisão obrigatória antes de merge
- Garante segurança e qualidade do código

### CI/CD — Integração Contínua / Entrega Contínua
- GitHub Actions configurado
- Testes automáticos a cada PR
- Proteção da branch `main`/`master`

---

## 🛠️ Estrutura de arquivos do projeto

```
medalerta/
├── Dockerfile
├── docker-compose.yml
├── .devcontainer/
│   └── devcontainer.json
├── .github/
│   └── workflows/
│       └── ci.yml
├── .env               ← nunca versionar!
├── .gitignore
├── .dockerignore
├── database/
│   └── init.sql
├── src/               ← desenvolvimento
├── docs/              ← documentação
└── tests/             ← testes
```

---

## 🚀 Comandos para execução

```bash
# Rodar os containers
docker compose up --build

# Rodar o projeto Java
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Rodar os testes Java
mvn test spring-boot:run -Dspring-boot.run.profiles=dev

# ⚠️ ATENÇÃO: apaga containers e dados!
docker compose down -v
```

---

## 🧰 Instalações necessárias

- Docker *(reiniciará o computador)*
- VSCode
- Git
- Extensão VSCode: Docker
- Extensão VSCode: DevContainer

### Configurações iniciais
- Criar novo repositório no GitHub
- Conectar VSCode ao GitHub
- Configurar PlantUML server para diagramas UML

---

## 👨‍🏫 Professor responsável

**Prof. Me. Guilherme Ditzel Patriota**
- Mestre em Engenharia Elétrica com IA aplicada
- Doutorando em Ciência da Computação — UdeS (Canadá)
- GitHub: [github.com/guipatriota](https://github.com/guipatriota)
- LinkedIn: [linkedin.com/in/guilhermepatriota](https://linkedin.com/in/guilhermepatriota)

---

*Bootcamp I — 2026 | Escola Politécnica/UNINTER*
