# quanto-custa-backend

Back-end que provê uma Web Api. Escrito com Java 8, Maven, Spring Boot 1.5.x, Spring Data Jpa e Spring Web. Necessita de banco de dados MySQL 5.6+.

# Começando 

## Primeiros passos
Esse projeto utiliza Maven para gerenciar seu processo de build e suas dependências, tornando-o agnóstico de IDEs ou editores. Use o que preferir.

## Banco de dados
O Quanto-Custa trabalha com o MySQl 5.x. O Flyway, integrado ao Spring Boot, é responsável por realizar a criação e alteração das tabelas. Entretanto, é necessaŕio a criação do schema básico. Execute o script /src/main/resources/migration/mysql/create-db.sql para criar a estrutura básica do BD.

## E-mail
Por hora apenas o Gmail, com as permissões necessárias na conta, é utilizado para enviar e-mails.


## Variáveis de ambiente
Configure as seguintes variáveis de ambientes, de acordo com seu sistema operacional, para habilitar CORS, banco de dados e e-mail:

### CORS
quantocusta_cors_origin
ex.: http://localhost:3000

### Banco de dados
quantocusta_db_user
ex.: root, minha_app

quantocusta_db_senha
ex.: 12345678, !%&d-0sd900!!

### E-mail
quantocusta_email_endereco
ex.: quantocusta@gmail.com, outro_email@gmail.com

quantocusta_email_senha
ex.: sua_senha_ao_seu_gosto

## Endpoint
A API estará publicada em seu host (ex.: localhost) na porta 8080. O endereço base completo, usando o localhost como exemplo: http://localhost:8080/quantocusta/api

## Desenvolvimento
Para executar esse projeto em modo de desenvolvimento, utilize:
- mvn spring-boot:run

## Produção
Para gerar o build final para produção use:
- mvn spring-boot:run

## Testes
Ainda não implementados.


## Documentação
A API está documentada com a utilização do Swagger. O endpoint de acesso é o /quantocusta/api/swagger-ui.html.