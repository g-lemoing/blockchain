package com.hackathon.blockchain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_status", nullable = false)
    private String accountStatus;
    @Column(nullable = false, unique = true)
    private String address;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private double netWorth;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Asset> assets;

    @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private WalletKey walletKey;

    public Wallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Asset> getAssets(){
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public WalletKey getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(WalletKey walletKey) {
        this.walletKey = walletKey;
    }
}
