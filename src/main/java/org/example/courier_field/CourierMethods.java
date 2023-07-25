package org.example.courier_field;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.*;
import static org.example.settings.Config.*;
import static org.apache.http.HttpStatus.*;


public class CourierMethods {

    CourierData cd = new CourierData();


    @Step("Создание нового курьера со всеми полями: LOGIN, PASSWORD, FIRSTNAME")
    public Response createNewCourier() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(cd.getCourierData())
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера только с обязательными полями: LOGIN, PASSWORD")
    public Response createCourierWithOnlyRequiredFields() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(cd.getCourierData_Login_Password())
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера без LOGIN")
    public Response createCourierWithoutLogin() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutLogin(cd.getPassword()))
                .post(COURIER_END_POINT);
    }

    @Step("Создание нового курьера без PASSWORD")
    public Response createCourierWithoutPassword() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutPassword(cd.getLogin()))
                .post(COURIER_END_POINT);
    }

    @Step("Создание курьера с уже существующем логином. Для начало нужно создать курьера, чтобы потом попробовать создать нового с таким же логином")
    public Response createCourierWithExistingLogin() {
        createNewCourier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(cd.getCourierData())
                .post(COURIER_END_POINT);
    }

    @Step("Получение логина курьера")
    public int getCourierLogin() {
        createNewCourier();
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(cd.getCourierData_Login_Password())
                .post(COURIER_LOGIN_END_POINT);
        response.then().statusCode(SC_OK);
        return response.then().extract().path("id");
    }

    @Step("курьер логинится в систему без валидного логина")
    public Response loginWithoutValidLoginField() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(new Courier(RandomStringUtils.randomAlphanumeric(9), cd.getPassword()))
                .post(COURIER_LOGIN_END_POINT);
    }

    @Step("курьер логинится в систему без логина ")
    public Response authWithoutLogin() {
        Courier courier = new Courier();
        return given()
                .header("Content-type", "application/json")
                .baseUri(MAIN_URL)
                .body(courier.getCourierWithoutLogin(cd.getPassword()))
                .post(COURIER_LOGIN_END_POINT);
    }

    @Step("Удаление курьера ")
    public Response deleteCourier(int id) {
        return given()
                .baseUri(MAIN_URL)
                .pathParam("id", id)
                .delete(COURIER_ID_END_POINT);
    }

    @Step("Удаление курьера с несуществующим id")
    public Response deleteCourierWithIncorrectId() {
        return given()
                .baseUri(MAIN_URL)
                .pathParam("id", Integer.MAX_VALUE)
                .delete(COURIER_ID_END_POINT);
    }

    @Step("Удаление курьера без id")
    public Response deleteCourierWithoutId() {
        return given()
                .baseUri(MAIN_URL)
                .delete("courier/");
    }


}
