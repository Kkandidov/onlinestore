package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class BackendLogicalException extends BackendException {
    public BackendLogicalException() {
        super();
    }

    public BackendLogicalException(String msg) {
        super(msg);
    }

    public BackendLogicalException(Throwable cause) {
        super(cause);
    }

    public BackendLogicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
