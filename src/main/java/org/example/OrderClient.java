package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private static final String PATH_CREATE_ORDER = "/api/v1/orders";
    private static final String PATH_LIST_ORDER = "/api/v1/orders";
    private static final String PATH_GET_ORDER_BY_ID = "/api/v1/orders/track";

    @Step("Отправить запрос на создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }

    @Step("Отправить запрос для получения списка заказов")
    public ValidatableResponse list(Map<String,String> orderQueryParam) {
        return given()
                .spec(getSpec())
                .queryParams(orderQueryParam)
                .when()
                .get(PATH_LIST_ORDER)
                .then();
    }

    @Step("Отправить запрос для получения заказа по треку")
    public ValidatableResponse getById(String track) {
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("t",track);
        return given()
                .spec(getSpec())
                .queryParams(queryParam)
                .when()
                .get(PATH_GET_ORDER_BY_ID)
                .then();
    }

}
