package com.demo.wms.domain;

import javax.persistence.*;

/**
 * Created by anton_kramarev on 8/12/2016.
 */

@Entity
public class ChunkItem {

    @EmbeddedId
    private ChunkItemKey key;


    private float quantity;
    private boolean defect;

    public ChunkItem() {
    }

    public ChunkItem(ChunkItemKey key) {
        this.key = key;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isDefect() {
        return defect;
    }

    public void setDefect(boolean defect) {
        this.defect = defect;
    }

    public ChunkItemKey getKey() {
        return key;
    }

    public void setKey(ChunkItemKey key) {
        this.key = key;
    }
}
