package com.demo.wms.repository;

import com.demo.wms.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findBySkuStartsWithIgnoreCase(String sku);
}
