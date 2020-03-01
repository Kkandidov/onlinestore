package org.astashonok.onlinestorebackend.exceptions.logicalexception;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;

public class NullReferenceToRequiredObject extends OnlineStoreLogicalException {
    public NullReferenceToRequiredObject() {
        super("Using a null reference here is prohibited! ");
    }

    public NullReferenceToRequiredObject(String msg) {
        super("Using a null reference here is prohibited! \n"  + msg);
    }
}
