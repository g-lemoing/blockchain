package com.hackathon.blockchain.repository;

import com.hackathon.blockchain.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findAssetsByWalletId(Long walletId);
}
