package org.example.OrderField;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.example.settings.Config.*;

public class OrderMethods {
    private static final String firstName = "123B";
    private static final String lastName = "sugrobov";
    private static final String address = "Konoha, 142 apt.";
    private static final int metroStation = 4;
    private static final String phone = "+7 800 355 35 35";
    private static final int rentTime = 5;
    private static final String deliveryDate = "2020-06-06";
    private static final String comment = "Saske, don`t come back to Konoha";
    private static String[] color;


    private static final Order orderData = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

    @Step("Создание нового заказа с определнным цветом")
    public static Response createOrder(String[] newColors) {
        color = newColors;
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(orderData)
                .post(ORDERS_END_POINT);
    }

    @Step("Получить список задач")
    public static Response getOrderList() {
        return given()
                .baseUri(MAIN_URL)
                .get(ORDERS_END_POINT);
    }
}
