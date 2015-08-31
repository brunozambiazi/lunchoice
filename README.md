# Lunchoice

## Resumo do projeto

A aplicação permite a votação em restaurantes sempre respeitando um limite de horário. Não tive pretensão de fazer algo usual do ponto de vista do frontend, justamente para permitir que se possa testar a funcionalidade da aplicação sem precisar fazer ficar fazendo login. Por isto, existe apenas uma tela na qual deve ser selecionado o usuário que estaria votando e o próprio restaurante escolhido.

A forma de aviso do restaurante escolhido é o envio de um e-mail através de um scheduler que fará a "contagem dos votos". Os três usuários cadastrados têm configurado um e-mail do Maillinator (mailinator.com), o qual é possível acessar pelos usernames person1-lunchoice, person2-lunchoice e person3-lunchoice. Todos devem receber um e-mail no momento em que o scheduler rodar (o envio de e-mais foi feito a partir do GMail, e a conta pode ser acessada conforme os dados contidos na classe ApplicationConfig).

Tanto o horário limite de votação quanto o momento da execução do schduler ficam configurados no application.properties. Por padrão, o limite é 11h30 da manhã e o scheduler roda um minuto depois.

## Camada de persistência

O "diferencial" do projeto ficou por conta da camada de persistência. Utilizei um módulo especial do Hibernate chamado OGM para realizar o mapeamento de entidades com um banco NoSQL, possibilitando a utilização de JPA e queries JPQL.

O banco utilizado foi o MongoDB, numa versão online e gratuita do mongolab.com, o qual permite visualizar todos os documentos salvos realizando login conforme os dados do persistence.xml. Este fato, em contrapartida, acabou causando lentidão nos testes unitários, visto que são efetuadas muitas operações de manipulação dos dados.

## Rodando

O projeto está configurado para rodar pelo Jetty, inicializando-o através do próprio Gradle. Para isto, executar: gradle build jettyRun
(Sugiro pular a etapa de testes quando esta não for necessária, devido a sua demora. Para isto, acrescentar: -x test)

## Melhorias

Algumas melhorias sugeridas:
* Utilização de logs;
* Separar o pacote "framework" em projeto à parte, sendo dependência da aplicação "Lunchoice";
* Testes unitários específicos para o pacote "framework";
* Cobertura de 100% das linhas de código por testes unitários;
* Utilização do Spring Social para login via Facebook, efetuando o aviso do restaurante escolhido através de notificações.