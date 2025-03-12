package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.dto.TransactionAssetDto;
import com.hackathon.blockchain.dto.WalletKeyResponseDto;
import com.hackathon.blockchain.exception.WalletNotFoundException;
import com.hackathon.blockchain.model.Transaction;
import com.hackathon.blockchain.model.User;
import com.hackathon.blockchain.model.Wallet;
import com.hackathon.blockchain.model.WalletKey;
import com.hackathon.blockchain.service.UserService;
import com.hackathon.blockchain.service.WalletKeyService;
import com.hackathon.blockchain.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> generateKeys(Authentication authentication,
                                               @RequestParam Long walletId){
        User user = userService.findAuthenticatedUser(authentication);
        Wallet wallet = walletService.getWalletById(walletId).orElseThrow();
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

    @PostMapping("/buy")
    public ResponseEntity<String> buyAssets(Authentication authentication,
                                            @RequestBody TransactionAssetDto transactionAssetDto) throws IOException, InterruptedException {
        User user = userService.findAuthenticatedUser(authentication);
        String response = walletService.buyAsset(user.getId(), transactionAssetDto.getSymbol(), transactionAssetDto.getQuantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellAssets(Authentication authentication,
                                            @RequestBody TransactionAssetDto transactionAssetDto) throws IOException, InterruptedException {
        User user = userService.findAuthenticatedUser(authentication);
        String response = walletService.sellAsset(user.getId(), transactionAssetDto.getSymbol(), transactionAssetDto.getQuantity());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map<String, List<Transaction>>> getTransactions(Authentication authentication,
                                                                          @RequestParam Long walletId){
        User user = userService.findAuthenticatedUser(authentication);
        Map<String, List<Transaction>> transactions = walletService.getWalletTransactions(walletId);
        if (transactions.containsKey("error")) throw new WalletNotFoundException(walletId);
        return new ResponseEntity<>(transactions, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getWalletBalance(Authentication authentication,
                                                                          @RequestParam Long userId) throws IOException, InterruptedException {
        User user = userService.findAuthenticatedUser(authentication);
        Map<String, Object> walletBalance = walletService.getWalletBalance(userId);
        if (walletBalance.containsKey("error")) throw new WalletNotFoundException(null);
        return new ResponseEntity<>(walletBalance, HttpStatusCode.valueOf(200));
    }
}
