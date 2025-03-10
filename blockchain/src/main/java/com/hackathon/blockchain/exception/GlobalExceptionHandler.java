package com.hackathon.blockchain.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    ProblemDetail problemDetail = null;

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException e){
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
        problemDetail.setProperty("description", "Invalid credentials");
        return problemDetail;
    }

    @ExceptionHandler(IOException.class)
    public ProblemDetail handleIOException(IOException e){
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), e.getMessage());
        problemDetail.setProperty("description", "A problem occured when storing / retrieving the key.");
        return problemDetail;
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ProblemDetail handleNoSuchAlgorithmException(NoSuchAlgorithmException e){
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
        problemDetail.setProperty("description", "A problem occured when generating the key.");
        return problemDetail;
    }

    @ExceptionHandler(InterruptedException.class)
    public ProblemDetail handleIOException(InterruptedException e){
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
        problemDetail.setProperty("description", "A problem occured when retrieving the market prices.");
        return problemDetail;
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ProblemDetail handleAssetNotFoundException(AssetNotFoundException e){
        problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
        problemDetail.setProperty("{\"message", "❌ Asset not found or price unavailable: " + e.getLocalizedMessage() + "}");
        return problemDetail;
    }
}
