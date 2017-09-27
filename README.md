# React Facebook Login Android
Este projeto tem por intuíto fornecer um plugin Android para intermediar o processo de login do Facebook utilizando o framework React Native.

## Instalação
Para a utilização deste plugin é necessário realizar a configuração de uma conta desenvolvedor Facebook, bem como executar os passos de criação de um app e hash de registro de acordo com a [API do Facebook](https://developers.facebook.com/docs/facebook-login/android) em sua aplicação Android gerada pelo React Native.

Após concluída a etapa de registro, execute os seguintes comandas para a configuração do plugin em seu projeto React Native.

```
npm install --save git+https://github.com/tmissao/ReactFacebookLoginAndroid.git // Adiciona a dependência do plugin no projeto
npm i // Instala todas as depências do projeto 
npm link // Cria o link de módulos do projeto React Native
react-native link // Cria o link de módulos no projeto Android
```

## Utilização
Para utilizar o plugin basta importar o módulo e chamar sua função *loginFacebook*, passando duas funções de callback, sendo que a primeira representa o caso de sucesso, a qual receberá o json com as informações do usuário. E o segundo callback representa o caso de erro, o qual recebá o stacktrace do erro.

``` javascript
import FacebookLogin from 'react-native-library-facebook-login' // Importação do módulo

<Button 
    title = "Connect on Facebook"
    onPress = {() => FacebookLogin.loginFacebook( // Chamada da função de login
            userData => alert(userData),
            error => alert(error)
        )}
    />
```

## Demo
Dentro deste repositório na pasta demo existe a pasta *DemoReactFacebookLogin* contendo uma aplicação React Native demonstrando a utilização da biblioteca.