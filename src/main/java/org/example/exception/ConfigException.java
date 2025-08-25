package org.example.exception;

public class ConfigException extends RuntimeException {
    public ConfigException(String message) {
        super(message);
    }
}