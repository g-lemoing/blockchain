package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.dto.SmartContractDto;
import com.hackathon.blockchain.model.SmartContract;
import com.hackathon.blockchain.service.SmartContractEvaluationService;
import com.hackathon.blockchain.service.SmartContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RestController
@Validated
@RequestMapping("/contracts")
public class SmartContractController {

    @Autowired
    private SmartContractService smartContractService;
    private SmartContractEvaluationService smartContractEvaluationService;

    @PostMapping("/create")
    public ResponseEntity<SmartContract> createContract(@Valid @RequestBody SmartContractDto contractDto)
            throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        SmartContract contract = smartContractService.createContract(contractDto);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<String> validateContract(@Valid @PathVariable long id)
            throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        String response = smartContractService.isContractValid(id) ? "valid" : "invalid";
        response = "{\"message\": \"Smart contract is " + response +"\"}";
        return ResponseEntity.ok(response);
    }

}
