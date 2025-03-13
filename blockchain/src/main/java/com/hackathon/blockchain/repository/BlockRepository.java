package com.hackathon.blockchain.repository;

import com.hackathon.blockchain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByBlockIndex(Long blockIndex);
}
