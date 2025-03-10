package com.hackathon.blockchain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.blockchain.exception.AssetNotFoundException;
import com.hackathon.blockchain.model.Asset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class MarketDataService {

    private final String apiUrl = "https://faas-lon1-917a94a7.doserverless.co/api/v1/web/fn-3d8ede30-848f-4a7a-acc2-22ba0cd9a382/default/fake-market-prices";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String getMarketPrices() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl)).GET().build();
        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getMarketPrice(String symbol) throws IOException, InterruptedException {
        String response = getMarketPrices();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Double> priceMap = objectMapper.readValue(response,
                    new TypeReference<Map<String, Double>>() {});
            double price = priceMap.get(symbol.toUpperCase());
            return "{\"message\": \"Current price of " + symbol + ": $" + price + "\"}";
        } catch (Exception e) {
            throw new AssetNotFoundException(symbol);
        }
    }
}