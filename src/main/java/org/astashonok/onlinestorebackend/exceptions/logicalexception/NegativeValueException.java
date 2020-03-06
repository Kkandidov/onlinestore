package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NegativeValueException extends OnlineStoreLogicalException {
    public NegativeValueException() {
        super();
    }

    public NegativeValueException(String message) {
        super("The value must not be negative! \n" + message);
    }
}
