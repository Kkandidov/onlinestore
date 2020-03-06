package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NotPositiveValueException extends OnlineStoreLogicalException {
    public NotPositiveValueException() {
        super();
    }

    public NotPositiveValueException(String message) {
        super(message);
    }
}
