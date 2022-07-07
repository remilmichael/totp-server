package me.remil.exception;

public class MissingItemException extends RuntimeException {

	public MissingItemException(String message) {
        super(message);
    }
}
