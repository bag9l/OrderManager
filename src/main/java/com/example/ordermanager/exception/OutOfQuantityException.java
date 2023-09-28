package com.example.ordermanager.exception;

public class OutOfQuantityException extends RuntimeException {
    private static final String OUT_OF_QUANTITY = "Insufficient amount.";

    public OutOfQuantityException(String message) {
        super(message.isEmpty() ? OUT_OF_QUANTITY : message);
    }

    public OutOfQuantityException() {
        super(OUT_OF_QUANTITY);
    }
}