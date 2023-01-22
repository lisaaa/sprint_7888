package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.virtualauthenticator.Credential;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class CourierLoginTest {
    private  Courier courier;
    private  Courier courierDefault;
    private  Courier courierWithoutNecessarilyFields;
    private  Courier courierWithoutPassword;
    private CourierClient courierClient;
    private int id;

    @Before
    public  void setUp(){
        courier = CourierGenerator.getRandomCourier();
        courierDefault = CourierGenerator.getDefaultCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("курьер может авторизоваться")
    @Description("200 успешная авторизация курьера")
    public void courierCanBeLogin(){
        ValidatableResponse loginResponse  =  courierClient.login(CourierCredentials.from(courierDefault));
        //System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK,statusCode);
    }

    @Test
    @DisplayName("Авторизация курьера без логина и пароль ")
    @Description("Ошибка при авторизации без логина и пароля")
    public void courierLoginWithoutFileds(){
        CourierCredentials loginCred = new CourierCredentials();
        ValidatableResponse loginResponse  =  courierClient.login(loginCred);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST,statusCode);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("400 Ошибка при авторизации без пароля")
    public void courierLoginWithoutPassword(){

        String login = courierDefault.getLogin();
        System.out.println(login);
        CourierCredentials courierCredentials = new CourierCredentials(login);
        ValidatableResponse loginResponse  =  courierClient.login(courierCredentials);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST,statusCode);
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("400 Ошибка при авторизации без логина")
    public void courierLoginWithoutLogin(){

        String psw = courierDefault.getPassword();
        System.out.println(psw);
        CourierCredentials courierCredentials = new CourierCredentials(psw);
        ValidatableResponse loginResponse  =  courierClient.login(courierCredentials);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST,statusCode);
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("404 Ошибка при авторизации с неправильным логином")
    public void courierLoginWrongLogin(){

        String psw = courierDefault.getPassword();
        System.out.println(psw);
        CourierCredentials courierCredentials = new CourierCredentials("555",psw);
        ValidatableResponse loginResponse  =  courierClient.login(courierCredentials);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND,statusCode);
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("404 Ошибка при авторизации с неправильным паролем")
    public void courierLoginWrongPassword(){

        String login = courierDefault.getLogin();
        System.out.println(login);
        CourierCredentials courierCredentials = new CourierCredentials(login,"555");
        ValidatableResponse loginResponse  =  courierClient.login(courierCredentials);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND,statusCode);
    }

    @Test
    @DisplayName("Авторизация несуществующим курьером")
    @Description("404 Ошибка при авторизации с несуществующим логином и паролем")
    public void courierLoginNonExistsClient(){

        String login = courierDefault.getLogin();
        System.out.println(login);
        CourierCredentials courierCredentials = new CourierCredentials("111","555");
        ValidatableResponse loginResponse  =  courierClient.login(courierCredentials);
        System.out.println(loginResponse.extract().body().asPrettyString());
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND,statusCode);
    }

    @Test
    @DisplayName("Проверить id курьера")
    @Description("При успешном логине возвращается ID курьера")
    public void courierIDLoginCheck(){

        ValidatableResponse loginResponse  =  courierClient.login(CourierCredentials.from(courierDefault));
        id = loginResponse.extract().path("id");
        assertNotNull (id);
    }

}
