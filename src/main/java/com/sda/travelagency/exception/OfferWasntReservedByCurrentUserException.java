package com.sda.travelagency.exception;

public class OfferWasntReservedByCurrentUserException extends RuntimeException{
    /**
     * Exception which is thrown when User want to reserve offer which wasnt reserved earlier by him
     * @param message
     */
    public OfferWasntReservedByCurrentUserException(String message) {
        super(message);
    }
}
