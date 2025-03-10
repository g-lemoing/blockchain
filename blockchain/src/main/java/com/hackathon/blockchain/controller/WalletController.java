package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.dto.WalletKeyResponseDto;
import com.hackathon.blockchain.exception.UserNotFoundException;
import com.hackathon.blockchain.model.User;
import com.hackathon.blockchain.model.Wallet;
import com.hackathon.blockchain.model.WalletKey;
import com.hackathon.blockchain.service.UserService;
import com.hackathon.blockchain.service.WalletKeyService;
import com.hackathon.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletKeyService walletKeyService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> create(Authentication authentication){
        User user = userService.findAuthenticatedUser(authentication);
        String message = "{\"message\": " + walletService.createWalletForUser(user) + "\"}";
        return ResponseEntity.status(200).body(message);
    }

    @PostMapping("/generate-keys")
    public ResponseEntity<String> generateKeys(Authentication authentication){
        User user = userService.findAuthenticatedUser(authentication);
        Wallet wallet = walletService.getWalletByUserId(user.getId()).orElseThrow();
        try{
            WalletKey walletKey = walletKeyService.createOrGenerateKeys(wallet);
            WalletKeyResponseDto walletKeyResponseDto = new WalletKeyResponseDto(
                    "Keys generated/retrieved successfully for wallet id: " + wallet.getId(),
                    walletKey.getPublicKey(), walletKeyService.getKeysFolder());
            return ResponseEntity.ok(walletKeyResponseDto.toString());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(500).body("A problem occurred when generating the key.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("A problem occurred when storing the key.");
        }
    }
}
