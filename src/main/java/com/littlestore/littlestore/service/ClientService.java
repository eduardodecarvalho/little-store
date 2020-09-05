package com.littlestore.littlestore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.littlestore.littlestore.domain.Client;
import com.littlestore.littlestore.domain.dto.ClientDTO;
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
        return clientRepository.findAll();
    }

    @Transactional
    public Integer create(ClientDTO clientDTO) {
        return clientRepository.save(clientDTO.getClient()).getId();
    }

    public void delete(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
        clientRepository.delete(client);
    }
}
