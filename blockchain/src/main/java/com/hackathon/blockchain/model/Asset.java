package com.hackathon.blockchain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false)
    private String symbol;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    public Asset(Long id, String symbol, double purchasePrice, double quantity, Wallet wallet) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.symbol = symbol;
        this.wallet = wallet;
    }

    public Asset() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

}
