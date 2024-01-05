package com.sda.travelagency.exception;

public class OfferAlreadyReservedException extends RuntimeException{
    /**
     * Exception which is thrown when User want to again reserve the same offer
     * @param message
     */
    public OfferAlreadyReservedException(String message) {
        super(message);
    }
}
