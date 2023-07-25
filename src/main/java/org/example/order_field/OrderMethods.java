package org.example.order_field;

import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;
import static org.example.settings.Config.*;

public class OrderMethods {
    private  final String firstName = "123B";
    private  final String lastName = "sugrobov";
    private  final String address = "Konoha, 142 apt.";
    private  final int metroStation = 4;
    private  final String phone = "+7 800 355 35 35";
    private  final int rentTime = 5;
    private  final String deliveryDate = "2020-06-06";
    private  final String comment = "Saske, don`t come back to Konoha";


    @Step("Создание нового заказа с определнным цветом")
    public  Response createOrder(String[] colors) {
        Order orderData = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(orderData)
                .post(ORDERS_END_POINT);
    }
    @Step("Получить заказ по его номеру")
    public  Response getOrderByTrack(int track) {
        return given()
                .baseUri(MAIN_URL)
                .queryParam("t",track)
                .get(ORDER_BY_TRACK_END_POINT);
    }
    @Step("Получить заказ без номера")
    public  Response getOrderWithoutTrack() {
        return given()
                .baseUri(MAIN_URL)
                .get(ORDER_BY_TRACK_END_POINT);
    }


    @Step("Получить весь список заказов")
    public  Response getOrderList() {
        return given()
                .baseUri(MAIN_URL)
                .get(ORDERS_END_POINT);
    }
    @Step("Отменить заказ")
    public   Response cancelTheOrderByTrack(int track){
        return  given().
                baseUri(MAIN_URL)
                .queryParam("track",track)
                .put(CANCEL_ORDER_BY_TRACK_END_POINT);
    }

    @Step("Принять заказ")
    public  Response acceptOrder(int courierId,int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .queryParam("courierId",courierId)
                .put(ACCEPT_ORDER_END_POINT);
    }
    @Step("Принять заказ без id курьера")
    public  Response acceptOrderWithoutCourierId(int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .put(ACCEPT_ORDER_END_POINT);
    }
    @Step("Принять заказ без id заказа")
    public  Response acceptOrderWithoutOrderId(int courierId){
        return  given()
                .baseUri(MAIN_URL)
                .queryParam("courierId",courierId)
                .put("orders/accept/");
    }
    @Step("Завершить заказ")
    public  Response finishOrder(int orderId){
        return  given()
                .baseUri(MAIN_URL)
                .pathParam("id",orderId)
                .put(FINISH_ORDER_END_POINT);
    }
}
