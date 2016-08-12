package com.demo.wms.services.order;

import com.demo.wms.domain.*;
import com.demo.wms.exeptions.OrderSubmitException;
import com.demo.wms.repository.ChunkRepository;
import com.demo.wms.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@SpringBootTest
@RunWith(value = SpringJUnit4ClassRunner.class)
public class OrderProcessorTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChunkRepository chunkRepository;
    @Autowired
    private OrderProcessor orderProcessor;

    public static final String TEST_PRODUCT = "Test product";
    public static final String SKU = "199/2";

    private Product product;
    @Before
    public void setUp() {
        product = new Product();
        product.setName(TEST_PRODUCT);
        product.setSku(SKU);
        productRepository.save(product);

        for(int i=1; i< 6; i++) {
            ChunkItem item = new ChunkItem(new ChunkItemKey((long)i, product.getId()));
            item.setQuantity((i + 5) * 1.2f);
            chunkRepository.save(item);
            product.addChunk(item);
        }
    }

    @Test
    public void shouldSubmitOrder() throws Exception, OrderSubmitException {
        Order order = new Order();

        ChunkItem one = chunkRepository.findOne(new ChunkItemKey(1L, product.getId()));
        ChunkItem two = chunkRepository.findOne(new ChunkItemKey(2L, product.getId()));

        order.addLine(new OrderLine(product, one, 1.1f));
        order.addLine(new OrderLine(product, two, 1.3f));
        orderProcessor.submitOrder(order);

    }

    @Test
    public void shouldCancelOrder() throws Exception {

    }

}