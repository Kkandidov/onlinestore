package org.astashonok.onlinestorebackend.dto.abstracts;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValueException;

public class Entity {
    protected long id;

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) throws NotPositiveValueException {
        if (id < 1){
            throw new NotPositiveValueException("id value must be from 1! ");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
