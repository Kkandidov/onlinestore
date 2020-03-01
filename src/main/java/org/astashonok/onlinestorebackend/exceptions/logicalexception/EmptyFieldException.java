package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class EmptyFieldException extends OnlineStoreLogicalException {
    public EmptyFieldException() {
        super("This field has to be filled! ");
    }

    public EmptyFieldException(String msg) {
        super("This field has to be filled! \n" + msg);
    }
}
