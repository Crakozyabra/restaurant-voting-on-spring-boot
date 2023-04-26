package com.example.restaurantVotingApplicationOnSpringBoot.error;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}