package com.littlestore.littlestore.service;

import com.littlestore.littlestore.domain.Client;
import com.littlestore.littlestore.repository.ClientRepository;
import com.littlestore.littlestore.utils.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
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
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client).getId();
    }

    public void delete(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessException.CLIENT_NOT_FOUND));
        clientRepository.delete(client);
    }
}
