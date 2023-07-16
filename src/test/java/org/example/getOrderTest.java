package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.example.OrderField.OrderMethods.*;
import static org.example.settings.Config.MAIN_URL;
import static org.example.settings.Config.ORDER_BY_TRACK_END_POINT;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class getOrderTest {
    public int orderTrack=0;
    public String[] color={"BLACK"};

    @Test
    @DisplayName("Получить заказ по его номеру")
    @Description("Получение информации о созданном заказе по полю track. Ожидается код ответа 200 и тело не пустое")
    public void getOrderByTrackTest() {
        Response CreateOrderResponse = createOrder(color);
        orderTrack = CreateOrderResponse.then().extract().path("track");
        Response getOrderBodyresponse = getOrderByTrack(orderTrack);
        getOrderBodyresponse.then().statusCode(200)
                .and()
                .body("order", notNullValue());

    }

    @Test
    @DisplayName("Получить заказ по несуществующему номеру")
    @Description("запрос с несуществующим заказом возвращает ошибку 404 и \"message\": \"Заказ не найден\" ")
    public void getOrderByNotExistingTrackTest() {
        Response getOrderBodyresponse = getOrderByTrack(Integer.MAX_VALUE);
        getOrderBodyresponse.then()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }

    @Test
    @DisplayName("Получить заказ без номера")
    @Description("запрос без номера заказа возвращает ошибку 400 и \"message\":  \"Недостаточно данных для поиска\" ")
    public void getOrderWithoutTrackTest() {
        getOrderWithoutTrack()
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @After
    @DisplayName("проверка что заказ удалился")
    public void cancelTheOrderTest() {
        if(orderTrack!=0){
            cancelTheOrderByTrack(orderTrack)
                    .then()
                    .statusCode(200)
                    .and()
                    .body("ok", equalTo(true));
        }
        }

}
