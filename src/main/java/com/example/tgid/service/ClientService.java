package com.example.tgid.service;


import com.example.tgid.model.Client;
import com.example.tgid.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> listarClientes() {
        return clientRepository.findAll();
    }
}
