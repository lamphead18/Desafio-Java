package com.example.tgid.controller;


import com.example.tgid.model.Transaction;
import com.example.tgid.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacao")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/{tipo}")
    public ResponseEntity<String> efetuarTransacao(@RequestBody @Valid Transaction transaction,
                                                   @PathVariable String tipo) {
        if ("deposito".equalsIgnoreCase(tipo)) {
            return transactionService.depositar(transaction);
        } else if ("saque".equalsIgnoreCase(tipo)) {
            return transactionService.sacar(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de transação inválido");
        }
    }


    }
