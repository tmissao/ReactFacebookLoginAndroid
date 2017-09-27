package br.com.missao.facebook.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Modulo responsável por providenciar o Login pelo Facebook
 */
public class FacebookLoginModule extends ReactContextBaseJavaModule {

    /**
     * Nome do módulo Nativo
     */
    private static final String MODULE_NAME = "FacebookLogin";

    /**
     * Mensagem de erro em caso de cancelamento do login
     */
    private static final String LOGIN_CANCELLED_MESSAGE = "Login cancelled";

    /**
     * Responsável para manipular o callback de retorno do Facebook
     */
    private CallbackManager callbackManager;

    /**
     * Responsável por ouvir o método onActivityResult da aplicação
     */
    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    };


    public FacebookLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>();
    }

    @ReactMethod
    public void loginFacebook(final Callback success, final Callback error) {

    /*
     * Permissão para leitura do email do usuário
     */
        final String READ_PERMISSION_EMAIL = "email";

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = new LoginButton(this.getCurrentActivity());
        loginButton.setReadPermissions(READ_PERMISSION_EMAIL);
        loginButton.registerCallback(callbackManager, getFacebookRegisterCallback(success, error));
        loginButton.performClick();
    }

    /**
     * Obtêm o {@link FacebookCallback} para manipular o resultado da requisição de registro do Facebook
     * @param jsSuccessCallback {@link Callback} a ser executado em caso de sucesso da operação
     * @param jsErrorCallback {@link Callback} em caso de falha da requisição
     * @return FacebookCallback
     */
    private FacebookCallback<LoginResult> getFacebookRegisterCallback(final Callback jsSuccessCallback,
                                                                      final Callback jsErrorCallback) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                requestUserInformation(loginResult, jsSuccessCallback);
            }

            @Override
            public void onCancel() {
                jsErrorCallback.invoke(LOGIN_CANCELLED_MESSAGE);
            }

            @Override
            public void onError(FacebookException error) {
                jsErrorCallback.invoke(getStackTraceString(error));
            }
        };
    }

    /**
     * Obtẽm as informações do Facebook do usuário
     *
     * @param session Informações da sessão do Facebook usuário
     */
    private void requestUserInformation(LoginResult session, final Callback jsSuccessCallback) {

        // Tipo da informação desejada
        final String informationType = "fields";

        // Especificação da informação desejada
        final String informationDesired = "id,name,email, picture.width(120).height(120)";

        GraphRequest dataRequest = GraphRequest.newMeRequest(session.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json, GraphResponse response) {
                        jsSuccessCallback.invoke(json.toString());
                    }
                });

        Bundle permissionParam = new Bundle();
        permissionParam.putString(informationType, informationDesired);
        dataRequest.setParameters(permissionParam);
        dataRequest.executeAsync();
    }

    /**
     * Concatena o StackTrace de uma exceção para uma String
     * @param e exceção a ser obtido o StackTrace
     * @return String representando o StackTrace
     */
    private String getStackTraceString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}