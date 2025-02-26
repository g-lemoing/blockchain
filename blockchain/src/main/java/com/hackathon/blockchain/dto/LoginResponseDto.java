package com.hackathon.blockchain.dto;

public record LoginResponseDto <T> (int httpStatus, String message, T data){

}
