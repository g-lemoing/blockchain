package com.hackathon.blockchain.model;

import com.hackathon.blockchain.utils.SignatureUtil;
import jakarta.persistence.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Entity
@Table(name = "block")
public class Block implements Comparable<Block> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "block_index")
    private long blockIndex;
    private String hash;
    @Column(name = "is_genesis")
    private boolean isGenesis;
    private long nonce;
    @Column(name = "previous_hash")
    private String previousHash;
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> pendingTransactions;
    private long timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(long blockIndex) {
        this.blockIndex = blockIndex;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isGenesis() {
        return isGenesis;
    }

    public void setGenesis(boolean genesis) {
        isGenesis = genesis;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(List<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String calculateHash() throws NoSuchAlgorithmException {
        String concatenatedStr = concatenateString();
        return SignatureUtil.generate256SHAStr(concatenatedStr);
    }

    public String concatenateString(){
        return getBlockIndex() +
                getPreviousHash() +
                getNonce() +
                getTimestamp();
    }

    @Override
    public int compareTo(Block o) {
        return (int) (this.getTimestamp() - o.getTimestamp());
    }
}
