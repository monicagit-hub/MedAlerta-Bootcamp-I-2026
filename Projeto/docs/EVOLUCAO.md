# 🚀 Evolução do Projeto — Além do Bootcamp

> Este documento registra a evolução pessoal do projeto MedAlerta após o Bootcamp I 2026, por iniciativa própria, com o objetivo de aplicar boas práticas de mercado e aprofundar conhecimentos em desenvolvimento backend.

---

## 💡 Motivação

O projeto entregue no bootcamp funcionava como um **console app** — um menu interativo no terminal. Apesar de funcional e didático, não representa o que é usado no mercado de trabalho.

A decisão foi evoluir o projeto para uma **API REST profissional**, mantendo toda a base construída (Models, Repositories e Services) e adicionando novas camadas e boas práticas.



> 📋 Para ver o projeto base do bootcamp, acesse o [README principal](/README.md)


---

## 🔄 O que mudou

### Console App → API REST

| Antes (Bootcamp) | Depois (Evolução) |
|-----------------|-------------------|
| Menu no terminal | Endpoints HTTP |
| Usuário digita no console | Requisições JSON via Postman |
| `App.java` com `CommandLineRunner` | Controllers REST com `@RestController` |
| Sem Tomcat | Tomcat embutido na porta 8080 |
| Sem autenticação | JWT com roles ADMIN e USER |
| Sem testes automatizados | 33 testes unitários e de integração |

---

## 📦 O que foi adicionado

### 1. Controllers REST (6 controllers)

```
controller/
├── UsuarioController.java
├── MedicamentoController.java
├── UsuarioMedicamentoController.java
├── HorarioController.java
├── AlertaController.java
└── RegistroController.java
```

### 2. Tratamento de erros

```
exception/
├── ErroResponse.java           ← molde do JSON de erro
└── GlobalExceptionHandler.java ← intercepta exceções
```

Retorno padronizado para erros:
```json
{ "status": 404, "mensagem": "Usuário não encontrado! ID: 999" }
{ "status": 400, "mensagem": "[nome: Nome é obrigatório]" }
```

### 3. Validações com Bean Validation

| Entidade | Campo | Anotação |
|----------|-------|----------|
| `Usuario` | nome, telefone, email | `@NotBlank`, `@Email` |
| `Medicamento` | nomeComercial | `@NotBlank` |
| `UsuarioMedicamento` | dosagem, formaUso | `@NotBlank` |
| `Horario` | horarioUso | `@NotNull` |
| `Alerta` | dataHorarioAlerta, statusAlerta | `@NotNull` |
| `Registro` | confirmacaoConsumo | `@NotNull` |

### 4. Documentação com Swagger/OpenAPI

Acesse em: `http://localhost:8080/swagger-ui/index.html`

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.6</version>
</dependency>
```

### 5. Testes automatizados (33 testes)

**Testes unitários (Services)** — usam `@Mock` para simular o banco:

```
UsuarioServiceTest             → 3 testes
MedicamentoServiceTest         → 3 testes
UsuarioMedicamentoServiceTest  → 3 testes
HorarioServiceTest             → 3 testes
AlertaServiceTest              → 3 testes
RegistroServiceTest            → 3 testes
```

**Testes de integração (Controllers)** — usam `MockMvc` para simular requisições HTTP:

```
UsuarioControllerTest          → 3 testes
MedicamentoControllerTest      → 3 testes
UsuarioMedicamentoControllerTest → 2 testes
HorarioControllerTest          → 2 testes
AlertaControllerTest           → 2 testes
RegistroControllerTest         → 2 testes
MedalertaApplicationTests      → 1 teste
                                = 33 testes ✅
```

Rodar todos os testes:
```bash
docker compose exec app mvn test
```

### 6. Segurança com JWT + Spring Security

```
security/
├── JwtUtil.java               ← gera e valida tokens
├── JwtFilter.java             ← intercepta requisições
├── UsuarioDetalhes.java       ← adapta Usuario para Spring Security
├── UsuarioDetalhesService.java ← carrega usuário por email
├── SecurityConfig.java        ← configura regras de acesso
└── AuthController.java        ← endpoints de autenticação
```

**Roles implementadas:**

| Role | Acesso |
|------|--------|
| `ADMIN` | Acessa todos os endpoints |
| `USER` | Acessa só `/auth/me` e seus próprios dados |

**Campos adicionados no `Usuario`:**
- `senha` — armazenada com `BCryptPasswordEncoder` (nunca em texto puro)
- `role` — `USER` (padrão) ou `ADMIN`

---

## 🌐 Endpoints disponíveis

### Autenticação (públicos — sem token)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/registro` | Cadastrar novo usuário |
| POST | `/auth/login` | Fazer login e receber token JWT |
| GET | `/auth/me` | Ver dados do usuário logado |

### Usuários (protegidos)

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| GET | `/usuarios` | ADMIN | Listar todos |
| GET | `/usuarios/{id}` | ADMIN, USER | Buscar por ID |
| POST | `/usuarios` | ADMIN | Cadastrar |
| DELETE | `/usuarios/{id}` | ADMIN | Deletar |

### Demais recursos (autenticados)

| Método | Endpoint |
|--------|----------|
| GET/POST/DELETE | `/medicamentos` |
| GET/POST/DELETE | `/vinculos` |
| GET/POST/DELETE | `/horarios` |
| GET/POST/DELETE | `/alertas` |
| GET/POST/DELETE | `/registros` |

---

## 🧪 Como testar

### 1. Subir o projeto

```bash
docker compose up -d
docker compose exec app mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 2. Registrar usuário admin

```json
POST http://localhost:8080/auth/registro
{
    "nome": "Monica",
    "telefone": "47984964545",
    "email": "monica@email.com",
    "senha": "123456",
    "role": "ADMIN"
}
```

### 3. Fazer login e pegar token

```json
POST http://localhost:8080/auth/login
{
    "email": "monica@email.com",
    "senha": "123456"
}
```

Resposta:
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

### 4. Usar o token nas requisições

No Postman: **Authorization → Bearer Token → colar o token**

---

## 🗺️ Próximos passos

| # | Melhoria | Status |
|---|----------|--------|
| 1 | Tratamento de erros (Exception Handler) | ✅ Concluído |
| 2 | Validações com Bean Validation | ✅ Concluído |
| 3 | Documentação com Swagger/OpenAPI | ✅ Concluído |
| 4 | Testes automatizados (33 testes) | ✅ Concluído |
| 5 | Segurança com Spring Security + JWT | ✅ Concluído |
| 6 | Roles ADMIN e USER | ✅ Concluído |
| 7 | Frontend com Angular | ⬜ Próximo |

---

## 📚 Aprendizados

- Arquitetura em camadas permite evoluir sem reescrever tudo
- `@RestController` substitui o `App.java` como ponto de entrada
- `spring-boot-starter-web` traz o Tomcat embutido e suporte HTTP
- `@Mock` e `MockMvc` permitem testar sem banco de dados real
- JWT autentica sem guardar sessão no servidor (stateless)
- `BCryptPasswordEncoder` garante que senhas nunca ficam em texto puro
- `@JsonIgnore` protege campos sensíveis no retorno da API
- Roles (`ADMIN`/`USER`) controlam o que cada perfil pode acessar

---

## 🔗 Links úteis

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
---

*Evolução pessoal — Abril 2026*