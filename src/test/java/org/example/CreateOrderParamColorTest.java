package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;
import static org.example.OrderField.OrderMethods.*;

@RunWith(Parameterized.class)
public class CreateOrderParamColorTest {
    @Parameterized.Parameter(0)
    public String[] color;

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
        createOrder(color)
                .then().statusCode(201)
                .and()
                .body("track", notNullValue());
    }

}
