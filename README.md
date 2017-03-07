# ALOIS README #

### What is this repository for? ###

##### Quick Summary #####
###### Repositório criado para versionar o codigo da fase de desenvolvimento do aplicativo Alois ######
##### Version #####
###### 0.0.1-SNAPSHOT ######

### How do I get set up? ###
#### Configuration ####
* Clonar o repositório;
* Haverão 3 pastas no repositorio sendo:
  - alois-mobile : Projeto do Android Studio contendo o codigo do app
  - alois-common : Projeto do eclipse contendo o codigo das entidades que são compartilhadas pelo app e pelo webservice
  - alois-solution: Projeto do eclipse contendo o codigo do webservice (API)
* Abrir o eclipse;
* No eclipse, acesse no menu Help->Marketplace e pesquise por Spring IDE;
* Clique no botão install e siga os passos para a instalação;
* Ao final da instalação reinicie o eclipse;
* Abrir no eclipse o projeto alois-common;
* Aguarde para que o maven resolva as dependencias, esse processo pode demorar;
* Clique o botão direito do mouse sobre o projeto e escolha a opção 'Run';
* Dentro da opção 'Run As' selecione "Maven install";
* Aguarde o build do projeto, ao final deve ser apresentada a mensagem "BUILD SUCCESS";
* Agora abra no eclipse o projeto alois-solution;
* Aguarde para que o maven resolva as dependencias, esse processo pode demorar;
* Depois abra o android studio e abra o projeto alois-mobile;
* Aguarde até que o gradle resolva as dependencias, esse processo pode demorar;
* Para o banco de dados apenas crie uma role chamada 'alois' com senha 'alois' com permissões de superusuario e permitido login;
  * A criação da estrutura do banco é feita pelo Hibernate portanto não precisamos nos preocupar com isso.
  
#### Run ####
* No eclipse clique com o botão direito sobre o projeto alois-solution;
* Selecione a opção 'Run As' e depois a opção "Spring Boot App";
* Aguarde até que uma mensagem "Application Started in..." seja exibida;
* Nesse momento o webservice estará rodando e aguardando solicitações;
* No Android Studio apenas clique no botão 'Run' e selecione o dispositivo no qual rodar o app;
  * É altamente recomendado o uso de um dispositivo fisico para testes para que o teste fique mais proximo da experiencia final.


### Contribution guidelines ###

* Depois de toda alteração no projeto alois-common é necessário que seja feito o "Maven install" para que o .jar do repositório local seja atualizado;

### Who do I talk to? ###

#### Victor Carvalho (victor.blq@gmail.com) ####
