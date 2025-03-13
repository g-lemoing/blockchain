package com.hackathon.blockchain.service;

import com.hackathon.blockchain.dto.SmartContractDto;
import com.hackathon.blockchain.exception.ContractNotFoundException;
import com.hackathon.blockchain.exception.WalletNotFoundException;
import com.hackathon.blockchain.model.SmartContract;
import com.hackathon.blockchain.model.Wallet;
import com.hackathon.blockchain.repository.SmartContractRepository;
import com.hackathon.blockchain.repository.WalletRepository;
import com.hackathon.blockchain.utils.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.*;
import java.util.Optional;

@Service
public class SmartContractService {
    @Autowired
    private SmartContractRepository contractRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletKeyService walletKeyService;
    @Autowired
    private SmartContractEvaluationService smartContractEvaluationService;

    public SmartContract createContract(SmartContractDto contractDto)
            throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        long walletId = contractDto.getIssuerWalletId();
        Optional<Wallet> walletOpt = walletRepository.findById(walletId);
        if(walletOpt.isEmpty()) throw new WalletNotFoundException(walletId);

        Wallet wallet = walletOpt.get();
        SmartContract contract = createNewContract(contractDto, wallet);
        String signature = getDigitalSignature(contractDto, wallet);
        contract.setDigitalSignature(signature);

        contractRepository.save(contract);
        return contract;
    }

    public Boolean isContractValid(long id){
        Optional<SmartContract> contractOptional = contractRepository.findById(id);
        if (contractOptional.isEmpty()) throw new ContractNotFoundException(id);
        return smartContractEvaluationService.verifyContractSignature(contractOptional.get());
    }

    private SmartContract createNewContract(SmartContractDto contractDto, Wallet wallet){
        SmartContract contract = new SmartContract();
        contract.setName(contractDto.getName());
        contract.setConditionExpression(contractDto.getConditionExpression());
        contract.setAction(contractDto.getAction());
        contract.setActionValue(contractDto.getActionValue());
        contract.setWallet(wallet);
        return contract;
    }

    private String getDigitalSignature(SmartContractDto smartContractDto, Wallet wallet)
            throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        PrivateKey privateKey = getPrivateKey(wallet);
        String dataToSign = smartContractDto.getName() +
                smartContractDto.getConditionExpression() +
                smartContractDto.getAction() +
                smartContractDto.getActionValue() +
                smartContractDto.getIssuerWalletId();
        byte[] hashedMessage = SignatureUtil.getHashedMessage(dataToSign);
        return SignatureUtil.getSignature(hashedMessage, privateKey);
    }

    private PrivateKey getPrivateKey(Wallet wallet) throws NoSuchAlgorithmException, IOException {
        PrivateKey key = walletKeyService.getPrivateKeyForWallet(wallet.getId());
        if(key == null){
            walletKeyService.generateAndStoreKeys(wallet);
            key = walletKeyService.getPrivateKeyForWallet(wallet.getId());
        }
        return key;
    }
}
