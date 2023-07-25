package org.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.order_field.OrderMethods;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

public class OrdersListTest {
    OrderMethods orderMethods;
    @Before
    public void setUp(){
        orderMethods=new OrderMethods();
    }
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение всего спика заказов. Ожидаем код 200 OK")
    public void getOrdersListTest(){
        orderMethods.getOrderList()
                .then()
                .statusCode(SC_OK);

    }

}
