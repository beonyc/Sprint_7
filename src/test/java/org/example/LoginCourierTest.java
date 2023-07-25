package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.courier_field.CourierMethods;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {
    public String RESPONSE_404_NOT_FOUND = "Учетная запись не найдена";
    public String RESPONSE_400_BAD_REQUEST = "Недостаточно данных для входа";
    int id;

    CourierMethods courierMethods;

    @Before
    public void setUp(){
        courierMethods=new CourierMethods();
    }
    @Test
    @DisplayName("Авторизация курьера в системе")
    @Description("Курьер успешно логинится в системе, ожидается ответ 200 OK и id не пустое")
    public void loginCourierTest() {
        id =courierMethods.getCourierLogin();
        clearData(id);
    }

    @Test
    @DisplayName("Авторизация курьера с невалидным логином")
    @Description("Курьер о авторизуется в систему с невалидным логином, ожидается ответ 404 Not Found и \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithoutLoginFieldTest() {
        courierMethods.loginWithoutValidLoginField()
                .then().body("message", equalTo(RESPONSE_404_NOT_FOUND))
                .and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация курьера без логина или пароля")
    @Description("Проверяем авторизацию с незаполненным логином или паролем. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для входа\"")
    public void courierAuthWithoutRequiredField() {
        courierMethods.authWithoutLogin()
                .then().body("message", equalTo(RESPONSE_400_BAD_REQUEST))
                .and().statusCode(SC_BAD_REQUEST);
    }

    @Step("Очистка базы данных, удаление курьера")
    public void clearData(int id) {
        courierMethods.deleteCourier(id).then()
                .statusCode(SC_OK)
                .and().body("ok", equalTo(true));
    }


}
