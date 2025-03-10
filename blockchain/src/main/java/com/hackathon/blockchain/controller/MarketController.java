package com.hackathon.blockchain.controller;

import com.hackathon.blockchain.service.MarketDataService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/market")
public class MarketController {

    @Autowired
    MarketDataService marketDataService;

    @GetMapping("/prices")
    public ResponseEntity<String> getMarketPrices() throws IOException, InterruptedException {
        String response = marketDataService.getMarketPrices();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/price/{symbol}")
    public ResponseEntity<String> getMarketPrices(@NotNull @PathVariable String symbol)
            throws IOException, InterruptedException {
        String response = marketDataService.getMarketPrice(symbol);
        return ResponseEntity.ok(response);
    }
}
