package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;

public class NegativeValueException extends BackendLogicalException {
    public NegativeValueException() {
        super();
    }

    public NegativeValueException(String message) {
        super("The value must not be negative! \n" + message);
    }
}
