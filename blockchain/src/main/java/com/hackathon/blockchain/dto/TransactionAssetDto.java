package com.hackathon.blockchain.dto;

import lombok.ToString;

@ToString
public class TransactionAssetDto {
    private String symbol;
    private double quantity;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
