package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order_field.OrderMethods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class GetOrderTest {
    public int orderTrack=0;
    public String[] color={"BLACK"};
    OrderMethods orderMethods;
    @Before
    public void setUp(){
        orderMethods=new OrderMethods();
    }

    @Test
    @DisplayName("Получить заказ по его номеру")
    @Description("Получение информации о созданном заказе по полю track. Ожидается код ответа 200 и тело не пустое")
    public void getOrderByTrackTest() {
        Response CreateOrderResponse = orderMethods.createOrder(color);
        orderTrack = CreateOrderResponse.then().extract().path("track");
        Response getOrderBodyresponse = orderMethods.getOrderByTrack(orderTrack);
        getOrderBodyresponse.then().statusCode(SC_OK)
                .and()
                .body("order", notNullValue());

    }

    @Test
    @DisplayName("Получить заказ по несуществующему номеру")
    @Description("запрос с несуществующим заказом возвращает ошибку 404 и \"message\": \"Заказ не найден\" ")
    public void getOrderByNotExistingTrackTest() {
        Response getOrderBodyresponse = orderMethods.getOrderByTrack(Integer.MAX_VALUE);
        getOrderBodyresponse.then()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    @DisplayName("Получить заказ без номера")
    @Description("запрос без номера заказа возвращает ошибку 400 и \"message\":  \"Недостаточно данных для поиска\" ")
    public void getOrderWithoutTrackTest() {
        orderMethods.getOrderWithoutTrack()
                .then()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @After
    @DisplayName("проверка что заказ удалился")
    public void cancelTheOrderTest() {
        if(orderTrack!=0){
            orderMethods.cancelTheOrderByTrack(orderTrack)
                    .then()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));
        }
        }

}
