package dev.miguel.registerapp.model;

public class Usuario {

    private String name;
    private String phone;
    private String email;

    public Usuario(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
