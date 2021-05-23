# Senior Test

API criada para o teste na entrevista da Senior.

## Run

A API esta com 3 perfis configurados `h2 , Dev, Prod`.

`h2` - Utiliza banco h2. O banco é criado quando o projeto é rodado e também são criados alguns registros para teste.

`Dev` - Utiliza banco PostgreSQL. As tabelas são atualizadas quando o projeto é rodado e são criados alguns registros para teste.

`Prod` - Utiliza banco PostgreSQL. Não atualiza o banco e não são criados registros ao rodar o projeto.

Para utilizar o banco PostgreSQL é necessario criar um novo Banco e atualizar o caminho nas properties de cada perfil.

## Swagger

Toda API esta documentada via Swagger e após rodar o projeto pode ser acessada atraves do link: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

