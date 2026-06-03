# 🚀 Evolução do Projeto — Além do Bootcamp

> Este documento registra a evolução pessoal do projeto MedAlerta após o Bootcamp I 2026, por iniciativa própria, com o objetivo de aplicar boas práticas de mercado e aprofundar conhecimentos em desenvolvimento fullstack.

---

## 💡 Motivação

O projeto entregue no bootcamp funcionava como um **console app** — um menu interativo no terminal. Apesar de funcional e didático, não representa o que é usado no mercado de trabalho.

A decisão foi evoluir o projeto para uma **aplicação fullstack profissional**, mantendo toda a base construída (Models, Repositories e Services) e adicionando novas camadas e boas práticas.

---

> 📋 Para ver o projeto base do bootcamp, acesse o [README principal](/README.md)

---

## 🔄 O que mudou

### Console App → Aplicação Fullstack

| Antes (Bootcamp) | Depois (Evolução) |
|-----------------|-------------------|
| Menu no terminal | Interface web Angular |
| Usuário digita no console | Formulários e tabelas na tela |
| `App.java` com `CommandLineRunner` | Controllers REST + Angular |
| Sem autenticação | JWT com roles ADMIN e USER |
| Sem testes automatizados | 33 testes unitários e de integração |
| Sem documentação | Swagger UI completo |

---

## 📦 Backend — O que foi adicionado

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

### 5. Testes automatizados (33 testes)

**Testes unitários (Services):**
```
UsuarioServiceTest             → 3 testes
MedicamentoServiceTest         → 3 testes
UsuarioMedicamentoServiceTest  → 3 testes
HorarioServiceTest             → 3 testes
AlertaServiceTest              → 3 testes
RegistroServiceTest            → 3 testes
```

**Testes de integração (Controllers):**
```
UsuarioControllerTest            → 3 testes
MedicamentoControllerTest        → 3 testes
UsuarioMedicamentoControllerTest → 2 testes
HorarioControllerTest            → 2 testes
AlertaControllerTest             → 4 testes (+ confirmar e cancelar)
RegistroControllerTest           → 2 testes
MedalertaApplicationTests        → 1 teste
                                  = 37 testes ✅
```

> Testes de controller usam H2 em memória (sem dependência de MySQL).
> `@MockBean JwtUtil` e `@MockBean UsuarioDetalhesService` adicionados para
> que o filtro JWT não bloqueie as requisições de teste.

### 6. Segurança com JWT + Spring Security

```
security/
├── JwtUtil.java                ← gera e valida tokens
├── JwtFilter.java              ← intercepta requisições
├── UsuarioDetalhes.java        ← adapta Usuario para Spring Security
├── UsuarioDetalhesService.java ← carrega usuário por email
├── SecurityConfig.java         ← configura regras de acesso
└── AuthController.java         ← endpoints de autenticação
```

| Role | Acesso |
|------|--------|
| `ADMIN` | Acessa todos os endpoints |
| `USER` | Acessa só seus próprios dados |

---

## 🌐 Frontend — Angular 19

### Estrutura

```
Frontend/src/app/
├── core/
│   ├── components/navbar/   ← navbar compartilhada
│   ├── services/            ← auth, usuario, medicamento...
│   ├── interceptors/        ← adiciona token JWT automaticamente
│   └── guards/              ← protege rotas autenticadas
└── pages/
    ├── login/               ← tela de login estilo Grey's Anatomy
    ├── dashboard/           ← painel principal
    ├── usuarios/            ← CRUD de usuários
    ├── medicamentos/        ← CRUD de medicamentos
    ├── vinculos/            ← vínculo paciente-medicamento
    ├── horarios/            ← horários de medicamentos
    ├── alertas/             ← alertas de consumo
    └── registros/           ← confirmação de consumo
```

### Páginas implementadas

| Página | Funcionalidades |
|--------|----------------|
| Login | Autenticação JWT, estilo Grey's Anatomy com Seattle |
| Dashboard | Contadores reais: medicamentos, horários, alertas pendentes e registros |
| Usuários | Listar, cadastrar, editar, deletar |
| Medicamentos | Listar, cadastrar, editar, deletar |
| Vínculos | Listar, cadastrar, deletar (usuário vem do JWT, sem dropdown) |
| Horários | Listar, cadastrar, deletar (frequência opcional) |
| Alertas | Cards com "Tomei"/"Não tomei" para pendentes + histórico |
| Registros | Histórico somente-leitura (criado automaticamente ao confirmar alertas) |

### Tecnologias utilizadas

- Angular 19 com componentes standalone
- TypeScript
- JWT via localStorage + interceptor HTTP
- Guards de rota para autenticação

---

## 🧪 Como rodar o projeto

### Backend

```bash
cd Projeto
docker compose up -d
docker compose exec app mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Frontend

```bash
cd Frontend
ng serve
```

Acesse: `http://localhost:4200`

### Primeiro acesso

```json
POST http://localhost:8080/auth/registro
{
    "nome": "Admin",
    "telefone": "47999990001",
    "email": "admin@email.com",
    "senha": "123456",
    "role": "ADMIN"
}
```

---

## 🗺️ Evolução — Status atual

| # | Melhoria | Status |
|---|----------|--------|
| 1 | Tratamento de erros (Exception Handler) | ✅ Concluído |
| 2 | Validações com Bean Validation | ✅ Concluído |
| 3 | Documentação com Swagger/OpenAPI | ✅ Concluído |
| 4 | Testes automatizados (37 testes) | ✅ Concluído |
| 5 | Segurança com Spring Security + JWT | ✅ Concluído |
| 6 | Roles ADMIN e USER | ✅ Concluído |
| 7 | Frontend com Angular 19 | ✅ Concluído |
| 8 | Scheduler automático de alertas | ✅ Concluído |
| 9 | Tela de alertas pendentes com confirmação | ✅ Concluído |
| 10 | Isolamento de dados por usuário autenticado | ✅ Concluído |
| 11 | Dashboard com dados reais e badge na navbar | ✅ Concluído |
| 12 | Notificações em tempo real (WebSockets) | ⬜ Futuro |
| 13 | Deploy em produção | ⬜ Futuro |

### Fluxo completo implementado

```
CONFIGURAÇÃO (feita uma vez):
Usuário → Medicamento → Vínculo → Horário

EXECUÇÃO AUTOMÁTICA:
@Scheduled roda a cada minuto
    ↓
Cria Alerta com status "emitido" para cada Horário do dia
    ↓
Badge vermelho aparece na Navbar
    ↓
Usuário acessa /alertas → vê cards com "Tomei" ou "Não tomei"
    ↓
Clica → Alerta muda de status + Registro criado automaticamente
    ↓
/registros mostra histórico de consumo
```

---

## 🔗 Links úteis

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Frontend: `http://localhost:4200`

---

*Evolução pessoal — Abril/Junho 2026*