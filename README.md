# ALOIS README #

##### Resumo Rapido #####
###### Repositório criado para versionar o codigo da fase de desenvolvimento do aplicativo Alois ######
##### Versão #####
###### 0.0.1-SNAPSHOT ######

### Como eu faço para rodar o alois? ###
#### Configuração ####
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
* Para o banco de dados crie uma role chamada 'alois' com senha 'alois' com permissões de superusuario e permitido login;
* Depois apenas crie uma database de nome 'alois';
  * A criação da estrutura do banco é feita pelo Hibernate portanto não precisamos nos preocupar com isso.
  
#### Rodar ####
* No eclipse clique com o botão direito sobre o projeto alois-solution;
* Selecione a opção 'Run As' e depois a opção "Spring Boot App";
* Aguarde até que uma mensagem "Application Started in..." seja exibida;
* Nesse momento o webservice estará rodando e aguardando solicitações;
* No Android Studio apenas clique no botão 'Run' e selecione o dispositivo no qual rodar o app;
  * É altamente recomendado o uso de um dispositivo fisico para testes para que o teste fique mais proximo da experiencia final.

### Guia para alterações ###
* Depois de toda alteração no projeto alois-common é necessário que seja feito o "Maven install" para que o .jar do repositório local seja atualizado;

### Guia para contribuição ###
1. Clone o repositório e crie um branch local com o nome de sua preferencia, mas que indique que é um branch de desenvolvimento;
2. `git checkout 'nome-do-branch'`;
3. Utilize **SEMPRE** o branch de desenvolvimento para alterar o codigo para evitar conflitos nos commits;
4. Após as alterações efetue o commit local;
  4.1. No branch de desenvolvimento execute `git add ...` e depois `git commit -m "mensagem-do-commit"`
5. Mude para o branch master e realize um `git pull`;
6. Depois mude para o branch de desenvolvimento e execute um `git rebase master`;
7. Se na saida não constar nenhum conflito, alterne para o branch master e execute `git merge nome-do-branch-desenv`;
8. Depois execute o  git push`;
9. Caso seja apresentado algum conflito no passo 6, resolva os conflitos, e execute `git rebase continue`;
10. Caso ainda existam conflitos resolva-os e execute novamente o passo 9;
11. Caso contrário execute os passos 7 e 8.

##### Dessa forma não haverão conflitos no branch master que é o branch que sempre estará com o codigo mais atualizado. #####

### Em caso de dúvidas: ###

#### Victor Carvalho (victor.blq@gmail.com) ####
