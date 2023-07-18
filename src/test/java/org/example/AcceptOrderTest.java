package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier_field.CourierMethods;
import org.example.order_field.OrderMethods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;


import static org.hamcrest.Matchers.*;


public class AcceptOrderTest {
    int courierId = 0;
    int orderTrack = 0;
    int orderId = 0;
    String[] colors = new String[]{"BLACK"};
    CourierMethods courierMethods;
    OrderMethods orderMethods;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
        orderMethods = new OrderMethods();

    }

    @Test
    @DisplayName("Принять заказ")
    @Description("Заказ успешно принимается. Ожидается ответ \"ok\": true")
    public void acceptOrderTest() {
        courierId = courierMethods.getCourierLogin();
        orderTrack = orderMethods.createOrder(colors).then().extract().path("track");
        orderId = orderMethods.getOrderByTrack(orderTrack).then().extract().path("order.id");
        orderTrack = 0;

        Response acceptResponse = orderMethods.acceptOrder(courierId, orderId);
        acceptResponse.then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Принять заказ без id курьера")
    @Description("Заказ не примется. Ожидается ответ 400 Bad Request  \"message\":  \"Недостаточно данных для поиска\"")
    public void acceptOrderWithoutCourierIdTest() {
        orderTrack = orderMethods.createOrder(colors).then().extract().path("track");
        orderId = orderMethods.getOrderByTrack(orderTrack).then().extract().path("order.id");

        Response acceptResponse = orderMethods.acceptOrderWithoutCourierId(orderId);
        acceptResponse.then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Принять заказ с несуществующим id заказа")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found \"message\": \"Заказа с таким id не существует\"")
    public void acceptOrderWithNotExistingOrderIdTest() {
        courierId = courierMethods.getCourierLogin();
        Response acceptResponse = orderMethods.acceptOrder(courierId, Integer.MAX_VALUE);
        acceptResponse.then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Заказа с таким id не существует"));
    }

    @Test
    @DisplayName("Принять заказ с несуществующим id курьера")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found \"message\": \"Курьера с таким id не существует\"")
    public void acceptOrderWithNotExistingCourierIdTest() {
        orderTrack = orderMethods.createOrder(colors).then().extract().path("track");
        orderId = orderMethods.getOrderByTrack(orderTrack).then().extract().path("order.id");
        Response acceptResponse = orderMethods.acceptOrder(Integer.MAX_VALUE, orderId);
        acceptResponse.then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Принять заказ без id заказа")
    @Description("Заказ не примется. Ожидается ответ 404 Not Found.")
    public void acceptOrderWithoutOrderIdTest() {
        courierId = courierMethods.getCourierLogin();
        Response acceptResponse = orderMethods.acceptOrderWithoutOrderId(courierId);
        acceptResponse.then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Not Found."));
    }

    @After
    public void deleteCreatedData() {
        if (orderTrack != 0) {
            orderMethods.cancelTheOrderByTrack(orderTrack).then()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));
            orderTrack = 0;
        }
        if (courierId != 0 && orderId != 0) {
            orderMethods.finishOrder(orderId).then()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));

            courierMethods.deleteCourier(courierId).then()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));
            courierId = 0;
            orderId = 0;
        }
        if (courierId != 0) {
            courierMethods.deleteCourier(courierId).then()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));
            courierId = 0;
        }

    }

}
