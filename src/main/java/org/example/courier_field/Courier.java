package org.example.courier_field;

public class Courier {
    private String login;
    private String password;
    private String firstName;
    private Courier courier;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public Courier() {
    }
    public Courier getCourierWithoutLogin(String password){
        courier=new Courier();
        courier.setPassword(password);
        return courier;
    }
    public Courier getCourierWithoutPassword(String login){
        courier=new Courier();
        courier.setLogin(login);
        return courier;
    }


}
