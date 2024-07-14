package org.example.book_service.exception;

public class IngredientInUseException extends RuntimeException {
    public IngredientInUseException(String message) {
        super(message);
    }
}
