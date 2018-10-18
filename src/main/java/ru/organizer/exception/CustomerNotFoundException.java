package ru.organizer.exception;

public class CustomerNotFoundException extends IllegalStateException {
    
    public CustomerNotFoundException(String description) {
        super(description);
    }
    
}
