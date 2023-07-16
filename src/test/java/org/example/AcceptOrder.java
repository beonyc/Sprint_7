package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.example.CourierField.CourierMethods.*;
import static org.example.OrderField.OrderMethods.*;
import static org.hamcrest.Matchers.*;


public class AcceptOrder {
    int courierId;
    int orderTrack;
    int orderId;
    String[] colors = new String[]{"BLACK"};

    @Test
    @DisplayName("Принять заказ")
    @Description("Заказ успешно принимается. Ожидается ответ \"ok\": true")
    public void acceptOrderTest() {
        courierId = login();
        orderTrack = createOrder(colors).then().extract().path("track");
        orderId = getOrderByTrack(orderTrack).then().extract().path("order.id");

        Response acceptResponse = acceptOrder(courierId, orderId);
        acceptResponse.then()
                .statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));
        DeleteCourier(courierId);
        FinishOrder(orderId);
    }

    @Test
    @DisplayName("Принять заказ без id курьера")
    @Description("Заказ не примется. Ожидается ответ 400 Bad Request  \"message\":  \"Недостаточно данных для поиска\"")
    public void acceptOrderWithoutCourierIdTest() {
        orderTrack = createOrder(colors).then().extract().path("track");
        orderId = getOrderByTrack(orderTrack).then().extract().path("order.id");

        Response acceptResponse = acceptOrderWithoutCourierId(orderId);
        acceptResponse.then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
        cancelOrder(orderTrack);
    }

    @Test
    @DisplayName("Принять заказ с несуществующим id заказа")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found \"message\": \"Заказа с таким id не существует\"")
    public void acceptOrderWithNotExistingOrderIdTest() {
        courierId = login();
        Response acceptResponse = acceptOrder(courierId, Integer.MAX_VALUE);
        acceptResponse.then()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказа с таким id не существует"));
        DeleteCourier(courierId);
    }

    @Test
    @DisplayName("Принять заказ с несуществующим id курьера")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found \"message\": \"Курьера с таким id не существует\"")
    public void acceptOrderWithNotExistingCourierIdTest() {
        orderTrack = createOrder(colors).then().extract().path("track");
        orderId = getOrderByTrack(orderTrack).then().extract().path("order.id");
        Response acceptResponse = acceptOrder(Integer.MAX_VALUE, orderId);
        acceptResponse.then()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id не существует"));
        cancelOrder(orderTrack);
    }

    @Test
    @DisplayName("Принять заказ без id заказа")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found.")
    public void acceptOrderWithoutOrderIdTest() {
        courierId = login();
        Response acceptResponse = acceptOrderWithoutOrderId(courierId);
        acceptResponse.then()
                .statusCode(404)
                .and()
                .body("message", equalTo("Not Found."));
        DeleteCourier(courierId);
    }

    @Step("Удаление созданного курьера")
    public void DeleteCourier(int courierId) {
        deleteCourier(courierId).then()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));

    }

    @Step("Удаление созданного заказа")
    public void FinishOrder(int orderId) {
        finishOrder(orderId).then()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));

    }

    @Step("Отмена созданного заказа")
    public void cancelOrder(int orderTrack) {
        cancelTheOrderByTrack(orderTrack).then()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));

    }
}
