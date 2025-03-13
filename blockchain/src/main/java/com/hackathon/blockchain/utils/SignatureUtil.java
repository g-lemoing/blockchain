package com.hackathon.blockchain.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class SignatureUtil {
    public static boolean verifySignature(String data, String digitalSignature, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        byte[] messageBytes = getHashedMessage(data);
        Signature verifSign = Signature.getInstance("SHA256withRSA");
        verifSign.initVerify(publicKey);
        verifSign.update(messageBytes);
        return verifSign.verify(Base64.getDecoder().decode(digitalSignature));
    }

    public static byte[] getHashedMessage(String stringContract)
            throws NoSuchAlgorithmException {
        byte[] messageBytes = stringContract.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(messageBytes);
    }

    public static String getSignature(byte[] hashedMessage, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hashedMessage);
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }

    public static String generate256SHAStr(String inputStr) throws NoSuchAlgorithmException {
        byte[] messageBytes = getHashedMessage(inputStr);
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
