package com.hackathon.blockchain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "smart_contract")
public class SmartContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String conditionExpression;
    private String action;
    private double actionValue;
    private String digitalSignature;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issuer_wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Long getIssuerWalletId(){
        return wallet.getId();
    }
}
