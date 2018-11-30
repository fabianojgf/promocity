# Promocity
Serviço de promoções geolocalizadas. 

Features
---

* Serviços para Usuários
* Serviços para Lojistas
* Serviços para Promoções
* Servicos para Cupons
* Identificar proximidade de localização do usuário para uma loja
* Envio de mensagem para usuário
* Envio de cupom para usuário

TODO
--- 

* Gerar imagem de QRCode para um cupom
* Serviços de CRUD de usuários
* Serviços de CRUD de lojistas
* Serviços de Controle de autenticação de usuários e lojistas
* Serviços de CRUD de promoções
* Serviços CRUD de cupons
* Camanda de segurança

Sobre as operações para execução da aplicação
---

1. Faça o clone do repositório.

2. Crie o banco promocity.
```
create database promocity
```
3. Rode o script security.sql para criar as tabelas users e authorities

4. Rode o script populausers.sql para inserir o usuário armando (usuário armando/armando)

5. Limpe o projeto via comando clean do maven.
```
$mvn clean
```
6. Compile o projeto via modo teste do maven. Com isso, as demais tabelas serão criadas e atualizadas. 
```
$mvn test
```
7. Rode o script populadados.sql para popular com dados de exemplos. Para que sejam inseridos dados de exemplos para lojas, promoções e cupons. 

8. Execute a classe principal (BackendApplication) do projeto via maven. 
```
$mvn spring-boot:run
```
9. Listagens com dados de teste:

http://localhost:8082/promocity 

mostra os seguintes recursos disponíveis

"http://localhost:8082/promocity/users"
"http://localhost:8082/promocity/users/{idUser}"
"http://localhost:8082/promocity/users/{idUser}/coupons"
"http://localhost:8082/promocity/users/{idUser}/location/{latitude}/{longitude}"
"http://localhost:8082/promocity/stores"
"http://localhost:8082/promocity/stores/{idStore}/promotions"
"http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}"
"http://localhost:8082/promocity/stores/{idStore}/reportpromotions/{idPromotion}"
"http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/coupons"
"http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/coupons/{idCoupon}"
"http://localhost:8082/promocity/stores/{idStore}/promotions/{idPromotion}/reportcoupons/{idCoupon}"

Referências
---

[1] Maven. Management of Builds and Dependencies. Available at https://maven.apache.org

[2] Spring Boot 1. It is a Java Framework (based on the Spring Platform) for web applications that use inversion control container for the Java platform. Available at https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security

[3] Jersey. It is an open source RESTful Web Services framework to facilitate development of RESTful Web services and their clients in Java, a standard and portable JAX-RS API. Availale at https://jersey.github.io 

[4] Spring Data JPA. Abstartion of data access. Available at https://docs.spring.io/spring-data/jpa/docs/current/reference/html

[5] Mysql 5. Database Management System. Available at https://dev.mysql.com/downloads/mysql

Dúvidas, questionamentos ou sugestões enviar email para armando@ufpi.edu.br
