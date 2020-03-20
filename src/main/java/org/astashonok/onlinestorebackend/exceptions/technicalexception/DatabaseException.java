package org.astashonok.onlinestorebackend.exceptions.technicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendTechnicalException;

public class DatabaseException extends BackendTechnicalException {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
