package ru.organizer.exception;

public class UnknownCommandException extends IllegalStateException {
    
    public UnknownCommandException(String description) {
        super(String.format("Неизвестная команда: %s\n", description));
    }
}
