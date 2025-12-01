## 📦 API de Controle de Estoque e Módulo de Vendas

Este projeto é uma extensão de uma API REST de Controle de Estoque desenvolvida em **Spring Boot**, utilizando **Spring Data JPA** e **H2/MySQL** (ou outro banco configurado via `application.properties`). O principal objetivo desta versão é integrar um novo **Módulo de Vendas**, implementando uma lógica transacional crítica: a **baixa automática do estoque** de produtos no momento do registro de uma venda, garantindo o **rollback** (reversão) em caso de insuficiência de estoque.

-----

## 🛠️ Tecnologias Utilizadas

  * **Linguagem:** Java 17+
  * **Framework:** Spring Boot 3.x
  * **Persistência:** Spring Data JPA / Hibernate
  * **Banco de Dados:** Configurado via `application.properties` (Geralmente H2 para desenvolvimento ou MySQL/PostgreSQL para produção).
  * **Ferramenta de Construção:** Maven (pom.xml)

-----

## 🏗️ Modelagem de Entidades Chave

As seguintes entidades foram criadas ou atualizadas para suportar o novo módulo de Vendas:

| Entidade | Relacionamento | Função |
| :--- | :--- | :--- |
| **`Cliente`** | N/A | Armazena dados básicos do cliente. |
| **`Venda`** | 1:n com `Cliente` | Representa a transação de venda. |
| **`Produto`** | 1:1 com `Estoque` | Produto sendo vendido. |
| **`Estoque`** | 1:1 com `Produto` | Armazena a `quantidade` disponível. |
| **`ItemVenda`** | n:m entre `Venda` e `Produto` | Tabela intermediária que registra a **quantidade vendida** e o **preço unitário** no momento da venda. |

-----

## 🔑 Lógica de Negócios Crítica (Baixa e Rollback)

A funcionalidade central do projeto reside no método de registro de venda, que é transacional:

1.  **Anotação Transacional:** O método de registro de venda é anotado com `@Transactional`.
2.  **Verificação:** Para cada item, o sistema verifica se a `quantidade` desejada é menor ou igual à `quantidade` disponível no `Estoque`.
3.  **Baixa:** Se o estoque for suficiente, a quantidade vendida é **subtraída** do estoque.
4.  **Rollback:** Se **qualquer** item da venda não tiver estoque suficiente, uma `EstoqueInsuficienteException` é lançada, forçando o **rollback** (reversão) de todas as alterações no banco de dados, **incluindo** as baixas de estoque que já haviam sido processadas para outros itens da mesma transação.

-----

## 🌐 Endpoints REST

A API expõe os seguintes endpoints principais para interagir com os novos módulos:

### 1\. Clientes (CRUD)

| Método | URL | Descrição | Corpo de Exemplo |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/clientes` | Cria um novo cliente. | `{ "nome": "...", "email": "..." }` |
| `GET` | `/api/clientes/{id}` | Busca cliente por ID. | N/A |
| `PUT` | `/api/clientes/{id}` | Atualiza dados do cliente. | `{ "nome": "...", "email": "..." }` |
| `DELETE` | `/api/clientes/{id}` | Remove um cliente. | N/A |

### 2\. Vendas (Registro e Consulta)

| Método | URL | Descrição | Corpo de Exemplo (VendaDTO) |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/vendas` | **Registra uma nova venda** e executa a baixa automática do estoque. | `{ "clienteId": 1, "itens": [ { "produtoId": 2, "quantidade": 5 } ] }` |
| `GET` | `/api/vendas/{id}` | Consulta detalhes de uma venda. | N/A |

-----

## 🚀 Como Inicializar e Testar

### Pré-requisitos

1.  Java JDK 17+ instalado.
2.  Maven instalado.
3.  Um editor de código (VS Code, IntelliJ IDEA).
4.  Ferramenta de teste de API (Postman/Insomnia).

### Passos de Inicialização

1.  **Clone o Repositório:**
    ```bash
    git clone https://github.com/Lc-Davila/ControleEstoque--20240313-.git
    cd ControleEstoque--20240313-.git
    ```
2.  **Configuração do Banco:** Verifique e ajuste as configurações de banco de dados no arquivo `src/main/resources/application.properties`. Se estiver usando H2, nenhuma configuração adicional é necessária.
3.  **Compilar e Rodar:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    O servidor deve iniciar na porta `8080`.

### Roteiro de Teste Funcional (Com Prova de Rollback)

Use o Postman para simular o cenário de falha crítica:

1.  **Crie um Produto:** Use `POST /api/produtos` para criar um produto com **estoque baixo** (ex: 10 unidades).
2.  **Venda de Sucesso:** Use `POST /api/vendas` para vender 5 unidades.
      * **Verificação:** O estoque deve ir para 5. (Status `201 Created`)
3.  **Venda de Falha (Rollback):** Use `POST /api/vendas` para tentar vender 10 unidades.
      * **Verificação:** O sistema deve retornar **HTTP 400 Bad Request** (Erro de Estoque Insuficiente).
      * **Prova de Rollback:** Consulte o produto novamente. A quantidade deve **permanecer em 5**, provando que a transação foi revertida.
