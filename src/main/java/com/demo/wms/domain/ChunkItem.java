package com.demo.wms.domain;

import javax.persistence.*;

/**
 * Created by anton_kramarev on 8/12/2016.
 */

@Entity
@IdClass(ChunkItemKey.class)
public class ChunkItem {

    @Id
    private Long id;

    @Id
    private Long productId;


    private float quantity;
    private boolean defect;

    public ChunkItem() {
    }

    public ChunkItem(Long productId) {
        this.productId = productId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return quantity +"m " + (isDefect() ? "defect: true" : "");
    }
}
