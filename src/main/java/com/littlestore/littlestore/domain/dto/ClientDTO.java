package com.littlestore.littlestore.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlestore.littlestore.domain.Client;
import com.littlestore.littlestore.domain.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class ClientDTO implements Serializable {

    private Client client;

    public ClientDTO() {
        client = new Client();
    }

    public ClientDTO(Client client) {
        this.client = client;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public Integer getId() {
        return client.getId();
    }

    public void setId(Integer id) {
        client.setId(id);
    }

    public String getName() {
        return client.getName();
    }

    public void setName(String name) {
        client.setName(name);
    }

    public Date getBirthDate() {
        if (client.getBirthDate() != null) {
            return Date.from(client.getBirthDate().atZone(ZoneOffset.UTC).toInstant());
        }
        return null;
    }

    public void setBirthDate(Date birthDate) {
        if (birthDate != null) {
            client.setBirthDate(LocalDateTime.ofInstant(birthDate.toInstant(), ZoneId.systemDefault()));
        }
    }

    public String getPassword() {
        return client.getPassword();
    }

    public void setPassword(String password) {
        client.setPassword(password);
    }

    public int getRole() {
        return client.getRole();
    }

    public void setRole(int role) {
        client.setRole(role);
    }

}
