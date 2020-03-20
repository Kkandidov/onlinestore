package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;

public class EmptyFieldException extends BackendLogicalException {
    public EmptyFieldException() {
        super();
    }

    public EmptyFieldException(String message) {
        super("This field must be filled ! \n" + message);
    }
}
