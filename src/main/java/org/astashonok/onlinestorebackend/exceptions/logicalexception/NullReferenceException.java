package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NullReferenceException extends OnlineStoreLogicalException {
    public NullReferenceException() {
        super();
    }

    public NullReferenceException(String message) {
        super("Use of null reference is prohibited! \n"  + message);
    }
}
