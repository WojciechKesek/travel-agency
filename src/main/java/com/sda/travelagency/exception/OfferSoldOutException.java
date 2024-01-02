package com.sda.travelagency.exception;

public class OfferSoldOutException extends RuntimeException{
    /**
     * Exception which is thrown when Airport object is not found in database
     * @param message
     */
    public OfferSoldOutException(String message) {
        super(message);
    }
}
