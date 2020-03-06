package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class OnlineStoreTechnicalException extends OnlineStoreException {
    public OnlineStoreTechnicalException() {
        super();
    }

    public OnlineStoreTechnicalException(String message) {
        super(message);
    }

    public OnlineStoreTechnicalException(Throwable cause) {
        super(cause);
    }

    public OnlineStoreTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
