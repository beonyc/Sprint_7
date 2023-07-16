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


    @Step("Создание нового заказа с определнным цветом")
    public static Response createOrder(String[] colors) {
        Order orderData = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(orderData)
                .post(ORDERS_END_POINT);
    }
    @Step("Получить заказ по его номеру")
    public static Response getOrderByTrack(int track) {
        return given()
                .baseUri(MAIN_URL)
                .queryParam("t",track)
                .get(ORDER_BY_TRACK_END_POINT);
    }
    @Step("Получить заказ без номера")
    public static Response getOrderWithoutTrack() {
        return given()
                .baseUri(MAIN_URL)
                .get(ORDER_BY_TRACK_END_POINT);
    }


    @Step("Получить весь список заказов")
    public static Response getOrderList() {
        return given()
                .baseUri(MAIN_URL)
                .get(ORDERS_END_POINT);
    }
    @Step("Отменить заказ")
    public  static Response cancelTheOrderByTrack(int track){
        return  given().
                baseUri(MAIN_URL)
                .queryParam("track",track)
                .put(CANCEL_ORDER_BY_TRACK_END_POINT);
    }

    @Step("Принять заказ")
    public static Response acceptOrder(int courierId,int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .queryParam("courierId",courierId)
                .put(ACCEPT_ORDER_END_POINT);
    }
    @Step("Принять заказ без id курьера")
    public static Response acceptOrderWithoutCourierId(int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .put(ACCEPT_ORDER_END_POINT);
    }
    @Step("Принять заказ без id заказа")
    public static Response acceptOrderWithoutOrderId(int courierId){
        return  given()
                .baseUri(MAIN_URL)
                .queryParam("courierId",courierId)
                .put("orders/accept/");
    }
    @Step("Завершить заказ")
    public static Response finishOrder(int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .put(FINISH_ORDER_END_POINT);
    }
}
