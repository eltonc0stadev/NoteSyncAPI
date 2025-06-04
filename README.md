# NoteSyncAPI

NoteSyncAPI é uma API desenvolvida para gerenciar e sincronizar anotações de forma simples, eficiente e segura. Este projeto foi criado como parte de um trabalho de extensão universitária, com o objetivo de proporcionar uma solução prática para estudantes e profissionais que precisam acessar e organizar suas notas em diferentes dispositivos.

> **Aviso:** O projeto ainda está em desenvolvimento e novas funcionalidades e tecnologias podem ser adicionadas a qualquer momento.

## Objetivo

O objetivo principal do NoteSyncAPI é fornecer um backend robusto e flexível para aplicações de anotações, permitindo o armazenamento, atualização, exclusão e sincronização de notas através de endpoints RESTful.

## Funcionalidades

- **Criação de notas**: Adicione novas anotações rapidamente.
- **Edição de notas**: Atualize o conteúdo das suas anotações a qualquer momento.
- **Exclusão de notas**: Remova anotações que não são mais necessárias.
- **Listagem de notas**: Visualize todas as suas anotações sincronizadas.
- **Sincronização**: Acesse suas notas em diferentes dispositivos, com garantia de integridade dos dados.

## Endpoints principais

| Método | Rota                                      | Descrição                                                                 |
|--------|-------------------------------------------|--------------------------------------------------------------------------|
| POST   | /api/notesync/auth/registro               | Registra um novo usuário                                                 |
| POST   | /api/notesync/auth/login                  | Realiza login e retorna o token JWT                                      |
| GET    | /api/notesync/nota/listar                 | Lista todas as notas do usuário autenticado                              |
| POST   | /api/notesync/nota/criar                  | Cria uma nova nota para o usuário autenticado                            |
| PUT    | /api/notesync/nota/deletar                | Move uma nota para a lixeira                                             |
| PUT    | /api/notesync/nota/restaurar              | Restaura uma nota da lixeira                                             |
| PUT    | /api/notesync/nota/arquivar               | Arquiva uma nota                                                         |
| PUT    | /api/notesync/nota/desarquivar            | Desarquiva uma nota                                                      |
| PUT    | /api/notesync/nota/atualizar              | Atualiza título, conteúdo ou status de uma nota                          |
| PUT    | /api/notesync/nota/atualizar-titulo       | Atualiza apenas o título de uma nota                                     |
| PUT    | /api/notesync/nota/atualizar-conteudo     | Atualiza apenas o conteúdo de uma nota                                   |
| PUT    | /api/notesync/nota/adicionar-amigo        | Adiciona usuários para compartilhar uma nota                             |
| PUT    | /api/notesync/nota/remover-amigo          | Remove usuários compartilhados de uma nota                               |
| GET    | /api/notesync/nota/arquivadas             | Lista todas as notas arquivadas do usuário autenticado                   |
| GET    | /api/notesync/nota/lixeira                | Lista todas as notas na lixeira do usuário autenticado                   |
| PUT    | /api/notesync/usuario/atualizar           | Atualiza dados do usuário autenticado                                    |
| PUT    | /api/notesync/usuario/desativar           | Desativa a conta do usuário autenticado                                  |
| PUT    | /api/notesync/usuario/ativar              | Reativa a conta do usuário autenticado                                   |
| PUT    | /api/notesync/usuario/atualizar-senha     | Atualiza a senha do usuário autenticado                                  |

> Todas as rotas (exceto registro e login) exigem autenticação via token JWT no header Authorization.

## Tecnologias Utilizadas

- Java
- Spring Boot Web
- Lombok
- Hibernate
- Spring Data JPA
- Docker
- Swagger
- AWS
- GitHub Actions para deploy contínuo
- Outros (novas tecnologias podem ser adicionadas durante o desenvolvimento)

## Como utilizar

1. **Clone o repositório**
    ```bash
    git clone https://github.com/eltonc0stadev/NoteSyncAPI.git
    ```

2. **Instale as dependências**
    - Certifique-se de ter o Java e o Docker instalados.
    - Instale as dependências do projeto com seu gerenciador de dependências preferido (ex: Maven ou Gradle).

3. **Configure as variáveis de ambiente**
    - Renomeie o arquivo `.env.example` para `.env` ou configure as propriedades necessárias no `application.properties`.

4. **Inicie a aplicação**
    ```bash
    ./mvnw spring-boot:run
    ```
    ou, via Docker:
    ```bash
    docker-compose up
    ```

5. **Acesse os endpoints**
    - Utilize ferramentas como Postman ou Insomnia para testar os endpoints RESTful.
    - A documentação Swagger estará disponível em `/swagger-ui.html` após a aplicação ser inicializada.

## Contribuição

Este projeto faz parte de um trabalho de extensão universitária, mas contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

## Licença

Copyright (c) 2025 Elton Costa

Este software e todo o seu conteúdo são propriedade exclusiva do(s) autor(es).  
É expressamente proibido copiar, reproduzir, modificar, distribuir, ou utilizar qualquer parte deste software para quaisquer fins, comerciais ou não, sem permissão prévia e por escrito do(s) autor(es).

**TODOS OS DIREITOS RESERVADOS.**

---

Desenvolvido por [eltonc0stadev](https://github.com/eltonc0stadev) para um trabalho de extensão da faculdade.
