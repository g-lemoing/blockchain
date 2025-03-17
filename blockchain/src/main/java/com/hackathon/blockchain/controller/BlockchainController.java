package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.model.Block;
import com.hackathon.blockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {
    @Autowired
    private BlockchainService blockchainService;

    @PostMapping("/mine")
    public ResponseEntity<String> mineTransactions() throws NoSuchAlgorithmException {
        String hash= blockchainService.mineTransactions();
        return ResponseEntity.ok("{\"message\": \"Block mined: " + hash + "\"}");
    }

    @GetMapping("/")
    public ResponseEntity<Block[]> getBlocks(){
        return ResponseEntity.ok(blockchainService.getBlocks());
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateBlockchain() throws NoSuchAlgorithmException {
        boolean isValid = blockchainService.isChainValid();
        String message = "{\"message\": \"Blockchain valid: " + String.valueOf(isValid) + "\"}";
        return ResponseEntity.ok(message);
    }
}
