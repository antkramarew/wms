package com.demo.wms.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@Entity
public class OrderLine {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;


    @PrimaryKeyJoinColumns({
            @PrimaryKeyJoinColumn(name = "chunkId", referencedColumnName = "chunkId"),
            @PrimaryKeyJoinColumn(name = "productId", referencedColumnName = "productId")
    })
    @ManyToOne
    private ChunkItem item;

    private float quantity;

    public OrderLine() {
    }

    public OrderLine(Product product, ChunkItem item, float quantity) {
        this.product = product;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public ChunkItem getItem() {
        return item;
    }

    public void setItem(ChunkItem item) {
        this.item = item;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
