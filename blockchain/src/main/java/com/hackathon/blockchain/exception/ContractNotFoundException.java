package com.hackathon.blockchain.exception;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(long id) {
        super(String.valueOf(id));
    }
}
