package com.hackathon.blockchain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_keys")
public class WalletKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
    @Column(name = "public_key", length = 512, nullable = false)
    private String publicKey;
    @Column(name = "private_key", length = 1800, nullable = false)
    private String privateKey;

    public WalletKey() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
