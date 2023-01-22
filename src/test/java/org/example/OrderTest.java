package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

public class OrderTest {
    private Order order;
    private Order orderWithoutColor;
    private OrderClient orderClient;
    private String trackActual;
    private String trackExpected;


    @Before
    public  void setUp(){
        orderClient = new OrderClient();
        orderWithoutColor = OrderDataGenerator.getOrderWithoutColor();

    }


    @ParameterizedTest
    @ValueSource(strings = {"","BLACK","GREY","BLACK, GREY"})
    public void orderCanBeCreated(String paramColor){
        String[] color = new String[1];
        color[0] = paramColor;
        order = OrderDataGenerator.getOrder(color);
        orderClient = new OrderClient();
        ValidatableResponse responseCreate = orderClient.create(order);
        //System.out.println(response.extract().body().asPrettyString());
        trackActual = responseCreate.extract().path("track").toString();
        //System.out.println("track = " + trackActual);
        int statusCode = responseCreate.extract().statusCode();
        assertEquals(SC_CREATED,statusCode);
        ValidatableResponse responseGetOrderById = orderClient.getById(trackActual);
        //System.out.println(responseGetOrderById.extract().body().asPrettyString());
        trackExpected = responseGetOrderById.extract().path("order.track").toString();
        assertEquals(trackExpected,trackActual);

    }


    @Test
    @DisplayName("Check status code of /users/me")
    public void orderGetList(){
        ValidatableResponse responseCreate = orderClient.create(orderWithoutColor);
        Map<String, String> orderQueryParam = new HashMap<>();
        orderQueryParam.put("limit","10");
        orderQueryParam.put("page","0");
        ValidatableResponse responseList = orderClient.list(orderQueryParam);
        int statusCode = responseList.extract().statusCode();
        assertEquals(SC_OK,statusCode);
        assertNotNull(responseList.extract().body().path("orders"));
    }
}
