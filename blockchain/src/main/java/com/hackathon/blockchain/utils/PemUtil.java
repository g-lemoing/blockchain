package com.hackathon.blockchain.utils;

import java.security.Key;
import java.util.Base64;

public class PemUtil {
    public static String toPEMFormat(Key key, String type){
        String keyType;
        if ("PUBLIC".equalsIgnoreCase(type)) {
            keyType = "PUBLIC KEY";
        } else if ("PRIVATE".equalsIgnoreCase(type)) {
            keyType = "PRIVATE KEY";
        } else {
            throw new IllegalArgumentException("Invalid key type. Use 'PUBLIC' or 'PRIVATE'.");
        }
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        StringBuilder pemFormat = new StringBuilder();
        pemFormat.append("-----BEGIN " + keyType + "-----\n");
        for (int i = 0; i < encodedKey.length(); i += 64) {
            pemFormat.append(encodedKey, i, Math.min(i + 64, encodedKey.length())).append("\n");
        }
        pemFormat.append("-----END " + keyType + "-----\n");
        System.out.println(pemFormat.toString().length());
        return pemFormat.toString();
    }
}
