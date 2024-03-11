package com.example.tgid.controller;


import com.example.tgid.model.Client;
import com.example.tgid.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<List<Client>> listarClientes() {
        List<Client> clients = clientService.listarClientes();
        return ResponseEntity.ok(clients);
    }


}
