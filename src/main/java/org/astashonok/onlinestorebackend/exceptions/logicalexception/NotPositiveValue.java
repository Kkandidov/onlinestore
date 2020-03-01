package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NotPositiveValue extends OnlineStoreLogicalException {
    public NotPositiveValue() {
        super();
    }

    public NotPositiveValue(String msg) {
        super(msg);
    }
}
