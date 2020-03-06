package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class OnlineStoreException extends Exception {
    public OnlineStoreException() {
        super();
    }

    public OnlineStoreException(String message) {
        super(message);
    }

    public OnlineStoreException(Throwable cause) {
        super(cause);
    }

    public OnlineStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
