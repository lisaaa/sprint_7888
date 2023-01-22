package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {
    private  Courier courier;
    private  Courier courierWithoutNecessarilyFields;
    private  Courier courierWithoutPassword;
    private CourierClient courierClient;
    private int id;

    @Before
    public  void setUp(){
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();
    }
    @After
    public void cleanUp(){
        courierClient.delete(id);
       // System.out.println("клиент удален id = " + id);
    }


    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Создать курьера, залогинится новым курьером")
    public void courierCanBeCreated(){
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse  =  courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        Boolean resultSuccess = response.extract().path("ok");
        assertEquals(SC_CREATED,statusCode);
        assertTrue(resultSuccess);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("409 ошибка при создании двух одинаковых курьеров")
    public void courierCantBeDublicate(){
        ValidatableResponse response = courierClient.create(courier);
        assertEquals(SC_CREATED,response.extract().statusCode());
        Courier courierDublicate = courier;
        ValidatableResponse responseDublicate = courierClient.create(courierDublicate);
        int statusCode = responseDublicate.extract().statusCode();
        assertEquals(SC_CONFLICT,statusCode);
        ValidatableResponse loginResponse  =  courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

    }
    @Test
    @DisplayName("для создания курьера, нужно передать все обязательные поля")
    @Description("400 ошибка если логин и пароль не были переданы")
    public void createCourierWithoutNecessarilyFields(){
        courierWithoutNecessarilyFields = CourierGenerator.getCourierWithoutLoginAndPassword();
        ValidatableResponse response = courierClient.create(courierWithoutNecessarilyFields);
        System.out.println(response.extract().body().asPrettyString());
        assertEquals(SC_BAD_REQUEST,response.extract().statusCode());
    }

    @Test
    @DisplayName("создание курьера без пароля")
    @Description("400 ошибка если пароль не передан")
    public void createCourierWithoutPassword(){
        courierWithoutPassword = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        System.out.println(response.extract().body().asPrettyString());
        assertEquals(SC_BAD_REQUEST,response.extract().statusCode());
    }

    @Test
    @DisplayName("создать пользователя с логином, который уже есть")
    @Description("409 ошибка при создании курьера с уже существующим логином")
    public void createCourierWithSameLogin(){
        ValidatableResponse response = courierClient.create(courier);
        Courier courierWithSameLogin = CourierGenerator.getRandomCourier();
        courierWithSameLogin.setLogin(courier.getLogin());
        ValidatableResponse responseSameLogin = courierClient.create(courierWithSameLogin);
        System.out.println(responseSameLogin.extract().body().asPrettyString());
        int statusCode = responseSameLogin.extract().statusCode();
        assertEquals(SC_CONFLICT,statusCode);
    }

}
