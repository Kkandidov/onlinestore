package org.astashonok.onlinestorebackend.exceptions.basicexception;

public class BackendTechnicalException extends BackendException {
    public BackendTechnicalException() {
        super();
    }

    public BackendTechnicalException(String message) {
        super(message);
    }

    public BackendTechnicalException(Throwable cause) {
        super(cause);
    }

    public BackendTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
