package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.example.CourierField.CourierMethods.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    public String RESPONSE_BAD_REQUEST = "Недостаточно данных для создания учетной записи";
    public String RESPONSE_CONFLICT = "Этот логин уже используется. Попробуйте другой.";

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Создание нового курьера и проверка на код 201-Created и ответ true ")
    public void createNewCourierTest() {
        createNewCourier()
                .then().body("ok", equalTo(true))
                .and().statusCode(201);
    }

    @Test
    @DisplayName("Повторное создание курьера с одинаковыми данными")
    @Description("Проверка что при создании нового курьера с уже существующими данными выдаст код 409-Сonflict. При создании второго курьера, в него помещаются даннее первого курьера.")
    public void createTheSameCourierTest() {
        createNewCourier();
        createNewCourier()
                .then().statusCode(409)
                .and().body("message", equalTo(RESPONSE_CONFLICT));
    }

    @Test
    @DisplayName("Создание курьера со всеми обязательными полями")
    @Description("Курьер создается только с обязательными полями: LOGIN, PASSWORD")
    public void createCourierWithOnlyRequiredFieldsTest() {
        createCourierWithOnlyRequiredFields()
                .then().body("ok", equalTo(true))
                .and().statusCode(201);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без логина, ожидается ответ: 400 Bad Request")
    public void createCourierWithoutLoginTest() {
        createCourierWithoutLogin()
                .then().body("message", equalTo(RESPONSE_BAD_REQUEST))
                .and().statusCode(400 );
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без логина, ожидается ответ: 400 Bad Request")
    public void createCourierWithoutPasswordTest() {
        createCourierWithoutPassword()
                .then().body("message", equalTo(RESPONSE_BAD_REQUEST))
                .and().statusCode(400 );
    }
    @Test
    @DisplayName("Создание курьера с уже существующем логином")
    @Description("Проверка что при создании нового курьера с уже существующим логином выдаст код 409-Сonflict")
    public void createCourierWithExistingLoginTest() {
        createCourierWithExistingLogin()
                .then().body("message", equalTo(RESPONSE_CONFLICT))
                .and().statusCode(409 );
    }

}