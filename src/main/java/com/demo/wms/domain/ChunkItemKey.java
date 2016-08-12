package com.demo.wms.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@Embeddable
public class ChunkItemKey implements Serializable {

    @NotNull
    @Column(name = "chunkId")
    private Long chunkId;
    @NotNull
    @Column(name = "productId")
    private Long productId;

    public ChunkItemKey() {
    }

    public ChunkItemKey(Long chunkId, Long productId) {
        this.chunkId = chunkId;
        this.productId = productId;
    }

    public Long getId() {
        return chunkId;
    }

    public void setId(Long id) {
        this.chunkId = id;
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

        if (chunkId != null ? !chunkId.equals(that.chunkId) : that.chunkId != null) return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;

    }

    @Override
    public int hashCode() {
        int result = chunkId != null ? chunkId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
