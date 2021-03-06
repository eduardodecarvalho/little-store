package com.littlestore.littlestore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.littlestore.littlestore.domain.Client;
import com.littlestore.littlestore.repository.ClientRepository;
import com.littlestore.littlestore.utils.BusinessException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findById(Integer id) {
        return clientRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
    }

    public List<Client> findAll() {
        return clientRepository.findAllByOrderByName();
    }

    @Transactional
    public void update(Client client) {
        if (clientRepository.existsById(client.getId())) {
            clientRepository.save(client);
        }
    }

    @Transactional
    public Integer create(Client client) {
        return clientRepository.save(client).getId();
    }

    public void delete(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
        clientRepository.delete(client);
    }
}
