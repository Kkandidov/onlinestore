package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NegativeValueException extends OnlineStoreLogicalException {
    public NegativeValueException() {
        super("The value here hasn't to be negative! ");
    }

    public NegativeValueException(String msg) {
        super("The value here hasn't to be negative! \n" + msg);
    }
}
