package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class OnlineStoreException extends Exception {
    public OnlineStoreException() {
        super();
    }

    public OnlineStoreException(String msg) {
        super(msg);
    }

    public OnlineStoreException(Throwable cause) {
        super(cause);
    }

    public OnlineStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
