/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Button,
  Image,
  View
} from 'react-native';
import FacebookLogin from 'react-native-library-facebook-login'

export default class DemoReactFacebookLogin extends Component {

  constructor(props) {
    super(props)
    this.state = {
      facebookId: "",
      userName: "",
      userEmail: "",
      userPhotoUrl: "_"
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Button  
          title = "Connect on Facebook"
          onPress = {() => FacebookLogin.loginFacebook(
            userData => this.showFacebookDate(userData),
            error => alert(error)
          )}
        />

        <View style = {{padding: 30}}>
          <Text> FacebookId: {this.state.facebookId} </Text>
          <Text> Name: {this.state.userName} </Text>
          <Text> Email: {this.state.userEmail} </Text>
          <Image style = {{width: 120, height: 120, marginTop: 30, alignSelf: 'center'}}
             source = {{uri: this.state.userPhotoUrl}} />
        </View>
      </View>
    );
  }

  showFacebookDate(userInfoJson) {
    let userInfo = JSON.parse(userInfoJson)
    this.setState({
      facebookId: userInfo.id,
      userName: userInfo.name,
      userEmail: userInfo.email,
      userPhotoUrl: userInfo.picture.data.url
    })
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('DemoReactFacebookLogin', () => DemoReactFacebookLogin);
