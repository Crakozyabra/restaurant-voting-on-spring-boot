package com.example.restaurantVotingApplicationOnSpringBoot.error;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
