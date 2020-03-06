package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class OnlineStoreLogicalException extends OnlineStoreException {
    public OnlineStoreLogicalException() {
        super();
    }

    public OnlineStoreLogicalException(String msg) {
        super(msg);
    }

    public OnlineStoreLogicalException(Throwable cause) {
        super(cause);
    }

    public OnlineStoreLogicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
