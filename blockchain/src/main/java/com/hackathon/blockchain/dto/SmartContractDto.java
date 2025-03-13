package com.hackathon.blockchain.dto;

import jakarta.validation.constraints.NotNull;

public class SmartContractDto {
    @NotNull
    private String name;
    @NotNull
    private String conditionExpression;
    @NotNull
    private String action;
    @NotNull
    private double actionValue;
    @NotNull
    private long issuerWalletId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getActionValue() {
        return actionValue;
    }

    public void setActionValue(double actionValue) {
        this.actionValue = actionValue;
    }

    public long getIssuerWalletId() {
        return issuerWalletId;
    }

    public void setIssuerWalletId(long issuerWalletId) {
        this.issuerWalletId = issuerWalletId;
    }
}
