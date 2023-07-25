package org.example.courier_field;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierData {
    private String login = RandomStringUtils.randomAlphanumeric(10);
    private String password = RandomStringUtils.randomAlphanumeric(10);
    private String firstName = RandomStringUtils.randomAlphanumeric(10);

    public Courier getCourierData() {
        return new Courier(getLogin(), getPassword(), getFirstName());
    }

    public Courier getCourierData_Login_Password() {
        return new Courier(getLogin(), getPassword());
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public CourierData() {
    }
}
