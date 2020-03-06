package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class EmptyFieldException extends OnlineStoreLogicalException {
    public EmptyFieldException() {
        super();
    }

    public EmptyFieldException(String message) {
        super("This field must be filled ! \n" + message);
    }
}
