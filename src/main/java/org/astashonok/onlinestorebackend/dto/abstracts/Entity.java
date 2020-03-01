package org.astashonok.onlinestorebackend.dto.abstracts;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValue;

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

    public void setId(long id) throws NotPositiveValue {
        if (id < 1){
            throw new NotPositiveValue("The id value has to be positive: from 1 to ...! ");
        }

        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
