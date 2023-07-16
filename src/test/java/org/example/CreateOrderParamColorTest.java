package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.example.OrderField.OrderMethods.*;

@RunWith(Parameterized.class)
public class CreateOrderParamColorTest {
    @Parameterized.Parameter(0)
    public String[] color;
    public static int orderTrack;

    @Parameterized.Parameters
    public static Object[][] getColorForOrder() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Позитивный сценарий: создание заказа с корректными данными")
    public void createOrderTest() {
        Response response = createOrder(color);
        response.then().statusCode(201)
                .and()
                .body("track", notNullValue());
        orderTrack = response.then().extract().path("track");
    }


    @After
    public void cancelTheOrderTest() {
        cancelTheOrderByTrack(orderTrack)
                .then()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

}
