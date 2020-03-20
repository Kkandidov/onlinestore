package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class BackendException extends Exception {
    public BackendException() {
        super();
    }

    public BackendException(String message) {
        super(message);
    }

    public BackendException(Throwable cause) {
        super(cause);
    }

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
