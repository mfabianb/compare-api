package com.example.compareapi.exception;

import static com.example.compareapi.constants.Constants.RESOURCE_NOT_FOUND;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super(RESOURCE_NOT_FOUND);
    }
}
