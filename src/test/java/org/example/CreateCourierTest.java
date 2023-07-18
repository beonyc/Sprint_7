package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import  org.example.courier_field.CourierMethods;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    int id = 0;
    public String RESPONSE_BAD_REQUEST = "Недостаточно данных для создания учетной записи";
    public String RESPONSE_CONFLICT = "Этот логин уже используется. Попробуйте другой.";
    public String RESPONSE_DELETE_NOT_FOUND = "Курьера с таким id нет.";
    CourierMethods courierMethods;

    @Before
    public void setUp(){
         courierMethods=new CourierMethods();
    }
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Создание нового курьера и проверка на код 201-Created и ответ true ")
    public void createNewCourierTest() {
        courierMethods.createNewCourier()
                .then().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);
        id = courierMethods.getCourierLogin();
    }

    @Test
    @DisplayName("Повторное создание курьера с одинаковыми данными")
    @Description("Проверка что при создании нового курьера с уже существующими данными выдаст код 409-Сonflict. При создании второго курьера, в него помещаются даннее первого курьера.")
    public void createTheSameCourierTest() {
        courierMethods.createNewCourier();
        courierMethods.createNewCourier()
                .then().statusCode(SC_CONFLICT)
                .and().body("message", equalTo(RESPONSE_CONFLICT));
    }

    @Test
    @DisplayName("Создание курьера со всеми обязательными полями")
    @Description("Курьер создается только с обязательными полями: LOGIN, PASSWORD")
    public void createCourierWithOnlyRequiredFieldsTest() {
        courierMethods.createCourierWithOnlyRequiredFields()
                .then().body("ok", equalTo(true))
                .and().statusCode(SC_CREATED);
        id = courierMethods.getCourierLogin();
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Попытка создать курьера без логина, ожидается ответ: 400 Bad Request")
    public void createCourierWithoutLoginTest() {
        courierMethods.createCourierWithoutLogin()
                .then().body("message", equalTo(RESPONSE_BAD_REQUEST))
                .and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Попытка создать курьера без логина, ожидается ответ: 400 Bad Request")
    public void createCourierWithoutPasswordTest() {
        courierMethods.createCourierWithoutPassword()
                .then().body("message", equalTo(RESPONSE_BAD_REQUEST))
                .and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера с уже существующем логином")
    @Description("Проверка что при создании нового курьера с уже существующим логином выдаст код 409-Сonflict")
    public void createCourierWithExistingLoginTest() {
        courierMethods.createCourierWithExistingLogin()
                .then().body("message", equalTo(RESPONSE_CONFLICT))
                .and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Description("Проверка что при удалении курьера с несуществующим id выдаст код 404 Курьера с таким id нет.")
    public void deleteCourierWitIncorrectIdTest() {
        courierMethods.deleteCourierWithIncorrectId()
                .then()
                .body("message", equalTo(RESPONSE_DELETE_NOT_FOUND))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
    @Test
    @DisplayName("Удаление курьера с без id")
    @Description("Проверка что при удалении курьера без id выдаст код 404 Not Found.")
    public void deleteCourierWithoutIdTest() {
        courierMethods.deleteCourierWithoutId()
                .then()
                .body("message", equalTo("Not Found."))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    @DisplayName("Удаление созданного курьера")
    @Description("Для получения id курьера нужно его залогинить, после мы получаем id и удаляем")
    public void clearData() {
        if (id != 0) {
            courierMethods.deleteCourier(id).then().statusCode(SC_OK)
                    .and().body("ok", equalTo(true));
        }

    }
}