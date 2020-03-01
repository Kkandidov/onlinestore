package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class IncorrectValueForField extends OnlineStoreLogicalException {
    public IncorrectValueForField() {
        super();
    }

    public IncorrectValueForField(String msg) {
        super(msg);
    }
}
