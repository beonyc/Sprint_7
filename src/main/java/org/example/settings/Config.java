package org.example.settings;

public class Config {
    public static final String MAIN_URL = "http://qa-scooter.praktikum-services.ru/api/v1/";
    public static final String COURIER_END_POINT = "courier";
    public static final String COURIER_LOGIN_END_POINT = COURIER_END_POINT + "/login";
    public static final String COURIER_ID_END_POINT = COURIER_END_POINT + "/{id}";
    public static final String INCORRECT_COURIER_ID_END_POINT = COURIER_END_POINT + "/";
    public static final String ORDERS_END_POINT = "orders";
    public static final String ORDER_BY_TRACK_END_POINT = "orders/track";
    public static final String CANCEL_ORDER_BY_TRACK_END_POINT = "orders/cancel";
    public static final String ACCEPT_ORDER_END_POINT="orders/accept/{id}";
    public static final String FINISH_ORDER_END_POINT="orders/finish/{id}";
}
