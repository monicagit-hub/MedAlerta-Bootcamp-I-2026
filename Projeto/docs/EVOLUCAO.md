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

---

## 📦 O que foi adicionado

### Controllers REST (6 controllers)

```
controller/
├── UsuarioController.java
├── MedicamentoController.java
├── UsuarioMedicamentoController.java
├── HorarioController.java
├── AlertaController.java
└── RegistroController.java
```

### Dependência adicionada no `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## 🌐 Endpoints disponíveis

### Usuários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/usuarios` | Listar todos |
| GET | `/usuarios/{id}` | Buscar por ID |
| POST | `/usuarios` | Cadastrar |
| DELETE | `/usuarios/{id}` | Deletar |

### Medicamentos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/medicamentos` | Listar todos |
| GET | `/medicamentos/{id}` | Buscar por ID |
| POST | `/medicamentos` | Cadastrar |
| DELETE | `/medicamentos/{id}` | Deletar |

### Vínculos (UsuarioMedicamento)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/vinculos` | Listar todos |
| GET | `/vinculos/{id}` | Buscar por ID |
| POST | `/vinculos` | Vincular usuário a medicamento |
| DELETE | `/vinculos/{id}` | Deletar |

### Horários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/horarios` | Listar todos |
| GET | `/horarios/{id}` | Buscar por ID |
| POST | `/horarios` | Cadastrar |
| DELETE | `/horarios/{id}` | Deletar |

### Alertas
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/alertas` | Listar todos |
| GET | `/alertas/{id}` | Buscar por ID |
| POST | `/alertas` | Cadastrar |
| DELETE | `/alertas/{id}` | Deletar |

### Registros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/registros` | Listar todos |
| GET | `/registros/{id}` | Buscar por ID |
| POST | `/registros` | Cadastrar |
| DELETE | `/registros/{id}` | Deletar |

---

## 🧪 Como testar

### Subir o projeto

```bash
docker compose up -d
docker compose exec app mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Exemplo de requisição POST para cadastrar usuário

```json
POST http://localhost:8080/usuarios

{
    "nome": "Ana Souza",
    "telefone": "41999990001",
    "email": "ana.souza@email.com",
    "enderecoRua": "Rua A",
    "enderecoNumero": 10,
    "enderecoBairro": "Centro",
    "enderecoCEP": "80000-001",
    "enderecoCidade": "Curitiba",
    "enderecoEstado": "PR"
}
```

---

## 🗺️ Próximos passos

| # | Melhoria | Status |
|---|----------|--------|
| 1 | Tratamento de erros (Exception Handler) | ✅ Concluído |
| 2 | Validações com Bean Validation | ✅ Concluído |
| 3 | Documentação com Swagger/OpenAPI | ⬜ Pendente |
| 4 | Testes automatizados (unitários e integração) | ⬜ Pendente |
| 5 | Segurança com Spring Security + JWT | ⬜ Pendente |

---

## 📚 Aprendizados

- Arquitetura em camadas permite evoluir sem reescrever tudo — Models, Repositories e Services não foram alterados
- `@RestController` substitui o `App.java` como ponto de entrada
- `spring-boot-starter-web` traz o Tomcat embutido e suporte a HTTP
- Uma API REST é testada com ferramentas como Postman em vez do terminal

---

*Evolução pessoal — Abril 2026*
