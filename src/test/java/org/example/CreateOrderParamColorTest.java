package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order_field.OrderMethods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamColorTest {
    @Parameterized.Parameter(0)
    public String[] color;
    public static int orderTrack;
    OrderMethods orderMethods;
    @Before
    public void setUp(){
        orderMethods=new OrderMethods();
    }

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
        Response response = orderMethods.createOrder(color);
        response.then().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
        orderTrack = response.then().extract().path("track");
    }


    @After
    public void cancelTheOrderTest() {
        orderMethods.cancelTheOrderByTrack(orderTrack)
                .then()
                .statusCode(SC_OK)
                .and()
                .body("ok", equalTo(true));
    }

}
