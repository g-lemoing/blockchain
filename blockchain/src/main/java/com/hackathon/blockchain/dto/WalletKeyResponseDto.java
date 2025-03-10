package com.hackathon.blockchain.dto;

public record WalletKeyResponseDto(
        String message,
        String publicKey,
        String absolutePath
) {

    @Override
    public String toString() {
        return "{" +
                "message:'" + message + '\'' +
                ", publicKey:'" + publicKey + '\'' +
                ", absolutePath=:" + absolutePath + '\'' +
                '}';
    }
}
