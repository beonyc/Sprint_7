package org.example.CourierField;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.*;
import static org.example.settings.Config.*;

public class CourierMethods {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(10);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(10);
    private static final String FIRSTNAME = RandomStringUtils.randomAlphanumeric(10);
    private static final Courier CourierData = new Courier(LOGIN, PASSWORD, FIRSTNAME);
    private static final Courier CourierData_Login_Password = new Courier(LOGIN, PASSWORD);

    @Step("Создание нового курьера со всеми полями: LOGIN, PASSWORD, FIRSTNAME")
    public static Response createNewCourier() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(CourierData)
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера только с обязательными полями: LOGIN, PASSWORD")
    public static Response createCourierWithOnlyRequiredFields() {
        Courier newCourierDataWIthRequiredFields = new Courier(RandomStringUtils.randomAlphanumeric(9), PASSWORD);
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(newCourierDataWIthRequiredFields)
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера без LOGIN")
    public static Response createCourierWithoutLogin() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutLogin(PASSWORD))
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера без PASSWORD")
    public static Response createCourierWithoutPassword() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutPassword(LOGIN))
                .post(COURIER_END_POINT);
    }

    @Step("Создание курьера с уже существующем логином. Для начало нужно создать курьера, чтобы потом попробовать создать нового с таким же логином")
    public static Response createCourierWithExistingLogin() {
        createNewCourier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(CourierData)
                .post(COURIER_END_POINT);
    }

    @Step("курьер логинится в систему")
    public static int login() {
        createNewCourier();
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(CourierData_Login_Password)
                .post(COURIER_LOGIN_END_POINT);
        response.then().statusCode(200);
        return response.then().extract().path("id");
    }

    @Step("курьер логинится в систему без валидного логина")
    public static Response loginWithoutValidLoginField() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(new Courier(RandomStringUtils.randomAlphanumeric(9), PASSWORD))
                .post(COURIER_LOGIN_END_POINT);
    }

    @Step("курьер логинится в систему без логина ")
    public static Response authWithoutLogin() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutLogin(PASSWORD))
                .post(COURIER_LOGIN_END_POINT);
    }

    @Step("Удаление курьера ")
    public static Response deleteCourier(int id) {
        return given()
                .baseUri(MAIN_URL)
                .pathParam("id", id)
                .delete(COURIER_ID_END_POINT);
    }

    @Step("Удаление курьера с несуществующим id")
    public static Response deleteCourierWithIncorrectId() {
        return given()
                .baseUri(MAIN_URL)
                .pathParam("id", Integer.MAX_VALUE)
                .delete(COURIER_ID_END_POINT);
    }
    @Step("Удаление курьера без id")
    public static Response deleteCourierWithoutId(){
        return given()
                .baseUri(MAIN_URL)
                .delete("courier/");
    }


}
