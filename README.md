# lunchoice

Obs.: a aplicação NÃO está funcional. Ela ficou literalmente pela metade devido a situações pessoais que me impossibilitaram de terminá-la há tempo.

A ideia era possibilitar o login pelo Facebook, demonstrando o Spring Social, para que às 11h30 da manhã o scheduler que computa os votos enviasse uma notificação do Facebook para o usuário, informando qual o restaurante mais votado naquela manhã. Não consegui tempo para fazer nada da parte da tela e nem incluir o Spring Social.

O "diferencial" do sistema ficou por conta da camada de persistência. Utilizei um módulo especial do Hibernate, chamado OGM, para realizar o mapeamento de entidades com um banco NoSQL (no caso, MongoDB), possibilitando a utilização de JPA e queries JPQL.

Algumas melhorias que poderiam ser feitas no código entregue (sem considerar, portanto, as coisas que nem foram feitas XD):
- Separar o pacote "framework" em projeto à parte, sendo dependência da aplicação "Lunchoice";
- Remover toda e qualquer configuração em XML, utilizando apenas as anotações do Spring;
- Melhoria dos testes unitários contemplando 100% das linhas de código;
- Comentários apropriados para geração de Javadoc.