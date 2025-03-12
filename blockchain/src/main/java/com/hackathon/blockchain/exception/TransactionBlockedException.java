package com.hackathon.blockchain.exception;

public class TransactionBlockedException extends RuntimeException {
    public TransactionBlockedException(String asset) {
        super(asset);
    }
}
