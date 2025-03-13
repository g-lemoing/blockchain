package com.hackathon.blockchain.exception;

public class AssetNotFoundException extends RuntimeException {
    public AssetNotFoundException(String asset) {
        super(asset);
    }
}
