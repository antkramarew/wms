package com.demo.wms.services;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.ChunkItemKey;
import com.demo.wms.domain.Product;
import com.demo.wms.repository.ChunkRepository;
import com.demo.wms.repository.ProductRepository;
import com.demo.wms.ui.views.form.ChunkForm;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created by toxa on 8/22/2016.
 */
@SpringComponent
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ChunkRepository chunkRepository;


    public Collection<Product> findBySKUStartsWithIgnoreCase(String sku) {
        return productRepository.findBySkuStartsWithIgnoreCase(sku);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }

    public void deleteChunk(Long chunkId, Long productId) {
        chunkRepository.delete(new ChunkItemKey(chunkId, productId));
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    public Collection<Product> findAll() {
        return  (Collection<Product>) productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Collection<ChunkItem> findProductChunks(Long productId) {
        return chunkRepository.findByProductId(productId);
    }

    public ChunkItem saveChunk(ChunkItem item) {

       return chunkRepository.save(item);
    }

    public ChunkItem findChunk(Long id, Long productId) {
        return chunkRepository.findOne(new ChunkItemKey(id, productId));
    }

    public void deleteChunk(ChunkItem item) {
        Product product = productRepository.findOne(item.getProductId());
        product.removeChunk(item);
        productRepository.save(product);
        chunkRepository.delete(item);
    }

}
