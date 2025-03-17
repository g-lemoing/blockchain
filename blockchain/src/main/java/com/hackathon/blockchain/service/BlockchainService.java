package com.hackathon.blockchain.service;

import com.hackathon.blockchain.exception.NoPendingTransactionsException;
import com.hackathon.blockchain.model.Block;
import com.hackathon.blockchain.model.Transaction;
import com.hackathon.blockchain.repository.BlockRepository;
import com.hackathon.blockchain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class BlockchainService {
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public boolean isChainValid() throws NoSuchAlgorithmException {
        List<Block> chain = blockRepository.findAll(Sort.by(Sort.Direction.ASC, "blockIndex"));

        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);
    
            String recalculatedHash = current.calculateHash();
            if (!current.getHash().equals(recalculatedHash)) {
                System.out.println("❌ Hash mismatch in block " + current.getBlockIndex());
                System.out.println("Stored hash: " + current.getHash());
                System.out.println("Recalculated: " + recalculatedHash);
                return false;
            }
    
            if (!current.getPreviousHash().equals(previous.getHash())) {
                System.out.println("❌ Previous hash mismatch in block " + current.getBlockIndex());
                return false;
            }
        }
    
        System.out.println("✅ Blockchain is valid");
        return true;
    }

    public Block[] getBlocks(){
        List<Block> blockList = blockRepository.findAll();
        return blockList.toArray(new Block[0]);
    }

    public Block newBlock(List<Transaction> pendingTransactions){
        long index = blockRepository.count();
        Block previousBlock = blockRepository.findAll()
                .stream().max(Comparator.comparing(Block::getTimestamp)).orElseThrow();
        String previousHash = previousBlock.getHash();

        Block block = new Block();
        block.setBlockIndex(index);
        block.setPendingTransactions(pendingTransactions);
        block.setPreviousHash(previousHash);
        return block;
    }

    public String mineTransactions() throws NoSuchAlgorithmException {
        List<Transaction> pendingTransactions = transactionRepository.findByStatus("PENDING");
        if(pendingTransactions.isEmpty()) throw new NoPendingTransactionsException();

        // Difficulty hash level = 4
        final String hashStartsWith = "0000";

        Block currentBlock = newBlock(pendingTransactions);
        Block block = mineBlock(currentBlock, hashStartsWith);

        block.setGenesis(false);
        block.setTimestamp(new Date().getTime());
        updateTransactions(block.getPendingTransactions(), "MINED");
        blockRepository.save(block);
        return block.getHash();
    }

    public Block mineBlock(Block block, String hashStartsWithDifficulty)
            throws NoSuchAlgorithmException {
        long nonce = 0;
        String hashStr = "";

        while (!block.getHash().startsWith(hashStartsWithDifficulty)) {
            block.setNonce(nonce);
            hashStr = block.calculateHash();
            block.setHash(hashStr);
            nonce++;
        }
        return block;
    }

    public void updateTransactions(List<Transaction> transactions, String newStatus){
        for(Transaction transaction : transactions){
            transaction.setStatus(newStatus);
            transactionRepository.save(transaction);
        }
    }
}
