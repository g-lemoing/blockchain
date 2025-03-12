package com.hackathon.blockchain.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super(String.valueOf(id));
    }
}
