package com.littlestore.littlestore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.littlestore.littlestore.domain.dto.ClientDTO;
import com.littlestore.littlestore.domain.dto.IdDTO;
import com.littlestore.littlestore.service.ClientService;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/v1/clients/{id}")
    public ClientDTO findById(@PathVariable Integer id) {
        return new ClientDTO(clientService.findById(id));
    }

    @GetMapping("/v1/clients")
    public List<ClientDTO> findClients() {
        return clientService.findAll().stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/v1/clients/{id}")
    public void update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        clientDTO.setId(id);
        clientService.create(clientDTO);
    }

    @PostMapping("/v1/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDTO create(@RequestBody ClientDTO clientDTO) {
        return new IdDTO(clientService.create(clientDTO));
    }

    @DeleteMapping("/v1/clients/{id}")
    public void update(@PathVariable Integer id) {
        clientService.delete(id);
    }
}
