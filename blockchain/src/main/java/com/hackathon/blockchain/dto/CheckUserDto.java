package com.hackathon.blockchain.dto;

public class CheckUserDto {
    private UserData user;

    public CheckUserDto() {
    }

    public CheckUserDto(String username) {
        this.user = new UserData(username);
    }

    public UserData getUser() {
        return user;
    }

    public void setUsername(UserData user) {
        this.user = user;
    }

    public static class UserData{
        private String username;

        public UserData() {
        }

        public UserData(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
