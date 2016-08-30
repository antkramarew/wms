package com.demo.wms.domain;

import java.io.Serializable;

/**
 * Created by anton_kramarev on 8/12/2016.
 */

public class ChunkItemKey implements Serializable {


    private Long id;

    private Long productId;

    public ChunkItemKey() {
    }

    public ChunkItemKey(Long productId) {
        this.productId = productId;
    }

    public ChunkItemKey(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkItemKey that = (ChunkItemKey) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
