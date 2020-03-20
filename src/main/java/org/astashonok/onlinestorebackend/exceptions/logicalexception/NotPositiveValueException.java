package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;

public class NotPositiveValueException extends BackendLogicalException {
    public NotPositiveValueException() {
        super();
    }

    public NotPositiveValueException(String message) {
        super(message);
    }
}
