package com.hackathon.blockchain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("No user found with user name " + username);
    }
}
