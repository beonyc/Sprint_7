package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.example.CourierField.CourierMethods.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    public String RESPONSE_404_NOT_FOUND = "Учетная запись не найдена";
    public String RESPONSE_400_BAD_REQUEST = "Недостаточно данных для входа";

    @Test
    @DisplayName("Авторизация курьера в системе")
    @Description("Курьер успешно логинится в системе, ожидается ответ 200 OK и id не пустое")
    public void loginCourierTest() {
        login()
                .then().body("id", notNullValue())
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Авторизация курьера с невалидным логином")
    @Description("Курьер о авторизуется в систему с невалидным логином, ожидается ответ 404 Not Found и \"message\": \"Учетная запись не найдена\"")
    public void loginCourierWithoutLoginFieldTest() {
        loginWithoutValidLoginField()
                .then().body("message", equalTo(RESPONSE_404_NOT_FOUND))
                .and().statusCode(404);
    }

    @Test
    @DisplayName("Авторизация курьера без логина или пароля")
    @Description("Проверяем авторизацию с незаполненным логином или паролем. Ожидаемый результат: код ответа 400 и сообщение об ошибке \"Недостаточно данных для входа\"")
    public void courierAuthWithoutRequiredField() {
        authWithoutLogin()
                .then().body("message", equalTo(RESPONSE_400_BAD_REQUEST))
                .and().statusCode(400);
    }

}
