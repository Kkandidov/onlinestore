package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;

public class NullReferenceException extends BackendLogicalException {
    public NullReferenceException() {
        super();
    }

    public NullReferenceException(String message) {
        super("Use of null reference is prohibited! \n"  + message);
    }
}
