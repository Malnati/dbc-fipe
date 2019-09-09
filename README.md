# dbc-fipe

## Enunciado proposto
* Criar um microserviço REST (utilizando Springboot, Java 8) que consuma a API de Consulta Tabela FIPE, que receba como parâmetro o ID de uma marca e o ID de um veículo e retorne o valor e o percentual da alteração do valor total do veículo ao longo dos anos em que o mesmo foi fabricado. Cada busca feita pelo usuário deve ser armazenada em um banco de dados MONGODB. O Frontend deve ser feito em Angular 6 +, o código deve ser disponibilizado em um repositório público (Gitlab de preferência). Para facilitar a correção, deve ser disponibilizado imagens utilizando Docker para quem for corrigir apenas subir as imagens, sem fazer configuração de ambiente para testar.

* Criar uma tela que possibilite a seleção da marca/modelo do veículo para serem passados por parâmetro para o serviço rest e mostre o resultado em tela.

* Serão avaliadas questões como boas práticas de desenvolvimento, legibilidade e manutenibilidade do código, "inteligência" da solução e design das classes;
 
* Documentação da API de Consulta Tabela FIPE: http://fipeapi.appspot.com/

## Executando a aplicação

A aplicação foi disponibilizada utilizando 3 containers básicos unidos em um docker-compose. Para executá-la basta executar os seguintes passos:

1. Clone - clonar o repositório 
2. Com o docker e o docker-compose devidamente instalados executar dentro da pasta raíz do projeto: `docker-compose up -d`.

As imagens serão construídas com os projetos e a aplicação será disponibilizada em `http:\\localhost:4200`.

## Melhorias

### Cache
Um dos requisitos solicitados era de que toda e qualquer chamada do cliente fosse cacheada em um banco de dados mongo. Recomendo utiliza a biblioteca spring *spring-boot-starter-cache* que possui suporte a caching utilizando diversos providers. Ainda não existe um provider default para mongodb, por isso escolhemos cachear o resultado de todas as consultas utilizando a lig *spring-data-mongodb* criando as entidades para cada objeto cacheado e estipulando o parâmetro **app.cache-ttl** para expiração do cache que pode ser definida no arquivo [application.yaml](./src/main/resources/application.yaml). 

Recomendamos o uso da biblioteca padrão para cache do próprio spring em conjunto com o REDIS, solução amplamente utilizada para esse fim e que possui performace comprovadamente superior a outros mecanismos de cache.

## Frontend
O container fipe-frontend foi disponibilizado utilizando o próprio angular-cli para execução, o que não é recomendado. Com um pouco mais de tempo para disponibilização poderíamos disponibilizar apenas o resultado do ng build em um servidor http (nginx, apache http ou hproxy), configurando um virtual host para o domínio a ser executado e um proxy reverso para o backend.





