package com.htp.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String  login;
    private String  password;
    private String  address;
    private Timestamp created;
    private Timestamp  changed;
    private String  phone;
    private String  email;

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(address, user.address) &&
                Objects.equals(created, user.created) &&
                Objects.equals(changed, user.changed) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, login, password, address, created, changed, phone, email);
    }
}
