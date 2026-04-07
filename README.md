# 🏰 Registro Oficial da Guilda de Aventureiros

Este projeto é o novo sistema de registro da Guilda de Aventureiros, desenvolvido para substituir os antigos pergaminhos por uma API REST robusta, consistente e segura, utilizando **Java** e **Spring Boot**. O sistema gerencia o ciclo de vida dos aventureiros e seus companheiros leais, respeitando todas as normas impostas pelo Conselho.

---

## 🛠️ Tecnologias Utilizadas
* **Java 17**
* **Spring Boot 3.2.0**
* **Maven** (Gerenciador de dependências)
* **ArrayList** (Persistência de dados em memória para fins de teste)

---

## 📋 Regras de Negócio Implementadas

### 🛡️ Aventureiros
* **Identificação:** O `id` é um identificador único, gerado automaticamente pelo sistema.
* **Classes Permitidas:** O sistema aceita exclusivamente `GUERREIRO`, `MAGO`, `ARQUEIRO`, `CLERIGO` e `LADINO`. Qualquer outro valor é rejeitado como inválido.
* **Nível:** Deve ser obrigatoriamente um valor maior ou igual a 1.
* **Estado:** Todo aventureiro inicia obrigatoriamente como **Ativo**. Quando um vínculo é encerrado, ele permanece no sistema como **Inativo** (`ativo = false`), garantindo que não desapareça da história.

### 🐾 Companheiros (Composição)
* **Ciclo de Vida:** O companheiro existe apenas como parte do aventureiro (composição). Não pode existir isoladamente nem ser compartilhado.
* **Espécies Permitidas:** `LOBO`, `CORUJA`, `GOLEM` e `DRAGAO_MINIATURA`.
* **Lealdade:** Valor inteiro validado estritamente entre 0 e 100.

---

## 🚀 Como Executar o Projeto

1.  Abra o projeto no **IntelliJ IDEA**.
2.  Certifique-se de que o Maven carregou todas as dependências (ícone do "M" no canto direito -> Reload).
3.  Execute a classe `ApiApplication.java` (botão Play verde).
4.  O servidor estará disponível em: `http://localhost:8080/aventureiros`

---

## 📡 Endpoints da API (Operações)

| Operação | Método | Endpoint | Descrição |
| :--- | :--- | :--- | :--- |
| **Registrar** | `POST` | `/aventureiros` | Registra um novo aventureiro (inicia ativo e sem pet). |
| **Listar** | `GET` | `/aventureiros` | Retorna lista resumida com filtros (classe, ativo, nível) e paginação. |
| **Consultar** | `GET` | `/aventureiros/{id}` | Retorna todos os dados, incluindo o companheiro, se existir. |
| **Atualizar** | `PUT` | `/aventureiros/{id}` | Atualiza exclusivamente nome, classe ou nível. |
| **Inativar** | `DELETE` | `/aventureiros/{id}/vinculo` | Encerra o vínculo com a guilda (`ativo = false`). |
| **Reativar** | `POST` | `/aventureiros/{id}/recrutar` | Altera o estado do aventureiro para `ativo = true`. |
| **Definir Pet** | `PUT` | `/aventureiros/{id}/companheiro` | Cria ou substitui o companheiro do aventureiro. |
| **Remover Pet** | `DELETE` | `/aventureiros/{id}/companheiro` | Remove o companheiro associado ao aventureiro. |

---

## 📦 Detalhes Técnicos de Implementação

* **Paginação via Headers:** Os metadados são retornados obrigatoriamente nos cabeçalhos da resposta:
  * `X-Total-Count`, `X-Page`, `X-Size`, `X-Total-Pages`.
* **Formato de Erro Padronizado:** Em caso de falha ou dados inválidos (ex: nível < 1), a API responde com:
  ```json
  {
    "mensagem": "Solicitação inválida",
    "detalhes": ["motivo do erro 1", "motivo do erro 2"]
  }
