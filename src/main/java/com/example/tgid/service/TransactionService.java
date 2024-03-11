package com.example.tgid.service;

import com.example.tgid.helper.Tarifa;
import com.example.tgid.model.Client;
import com.example.tgid.model.Empresa;
import com.example.tgid.model.Transaction;
import com.example.tgid.repository.ClientRepository;
import com.example.tgid.repository.EmpresaRepository;
import com.example.tgid.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    EmpresaRepository empresaRepository;
    @Autowired
    TransactionRepository transactionRepository;



    public ResponseEntity<String> depositar(Transaction transaction) {
        try {
            Client clientEncontrado = clientRepository.getReferenceById(transaction.getCliente().getId());
            Empresa empresaEncontrada = empresaRepository.getReferenceById(transaction.getEmpresa().getId());

            if (clientEncontrado == null || empresaEncontrada == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Depósito não efetuado. Cliente ou Empresa não encontrados");
            }

            Tarifa tarifa = new Tarifa(transaction.getValor());

            Transaction transactionSalva = new Transaction();
            transactionSalva.setCliente(clientEncontrado);
            transactionSalva.setEmpresa(empresaEncontrada);
            transactionSalva.setValor(tarifa.getValorReajustado());
            transactionSalva.setDataHora(LocalDateTime.now());
            empresaEncontrada.setSaldo(empresaEncontrada.getSaldo().add(transactionSalva.getValor()));

            transactionRepository.save(transactionSalva);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Depósito efetuado " + transactionSalva.getValor().toString() + " " +
                            "na conta da empresa : " + transactionSalva.getEmpresa().getNome() +
                            " pelo cliente: " + transactionSalva.getCliente().getNome());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não localizada");
        }
    }

    public ResponseEntity<String> sacar(Transaction transaction) {
        try{
            Client clientEncontrado = clientRepository.getReferenceById(transaction.getCliente().getId());
            Empresa empresaEncontrada = empresaRepository.getReferenceById(transaction.getEmpresa().getId());

            if (clientEncontrado == null || empresaEncontrada == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Depósito não efetuado. Cliente ou Empresa não encontrados");
            }

            if (empresaEncontrada.getSaldo().compareTo(transaction.getValor()) < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Depósito não efetuado. Saldo insuficiente");

            }

            Tarifa tarifa = new Tarifa(transaction.getValor());

            Transaction transactionSalva = new Transaction();
            transactionSalva.setCliente(clientEncontrado);
            transactionSalva.setEmpresa(empresaEncontrada);
            transactionSalva.setValor(tarifa.getValorReajustado().negate());
            transactionSalva.setDataHora(LocalDateTime.now());
            empresaEncontrada.setSaldo(empresaEncontrada.getSaldo().add(transactionSalva.getValor()));

            transactionRepository.save(transactionSalva);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Saque efetuado "+ transactionSalva.getValor().toString()+ " "+
                            "na conta da empresa : "+ transactionSalva.getEmpresa().getNome()+
                            " pelo cliente: "+ transactionSalva.getCliente().getNome());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não localizada");
        }
    }
}
