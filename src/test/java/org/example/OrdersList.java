package org.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.example.OrderField.OrderMethods.*;
public class OrdersList {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение всего спика заказов. Ожидаем код 200 OK")
    public void getOrdersListTest(){

        getOrderList()
                .then()
                .statusCode(200);


    }

}
