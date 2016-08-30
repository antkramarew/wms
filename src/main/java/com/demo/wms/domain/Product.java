package com.demo.wms.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String sku;
    private String name;
    private float width;
    private int price;
    private int tradePrice;

    public Product() {
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId" ,referencedColumnName = "id")
    private List<ChunkItem> chunks = new LinkedList<>();


    public void addChunk(ChunkItem item) {
        chunks.add(item);
    }

    public void removeChunk(ChunkItem item) {
        chunks.remove(item);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(int tradePrice) {
        this.tradePrice = tradePrice;
    }

    public List<ChunkItem> getChunks() {
        return chunks;
    }

    public void setChunks(List<ChunkItem> chunks) {
        this.chunks = chunks;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getStock() {
        float stock = 0f;
        for (ChunkItem chunk : chunks) {
            stock+= chunk.getQuantity();
        }
        return stock;
    }

    public String toString() {
        return sku + " " + name + " " + width +"m";
    }
}
