package com.example.restaurantVotingApplicationOnSpringBoot.error;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg);
    }
}
