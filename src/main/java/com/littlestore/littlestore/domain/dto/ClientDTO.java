package com.littlestore.littlestore.domain.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.littlestore.littlestore.domain.Client;

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

    public LocalDateTime getBirthDate() {
        return client.getBirthDate();
    }

    public void setBirthDate(LocalDateTime birthDate) {
        client.setBirthDate(birthDate);
    }

}
