package com.demo.wms.services;

import com.demo.wms.domain.*;
import com.demo.wms.exeptions.OrderSubmitException;
import com.demo.wms.processor.OrderProcessor;
import com.demo.wms.repository.ChunkRepository;
import com.demo.wms.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.matchers.JUnitMatchers.hasItem;

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

    @Autowired
    private OrderService orderService;

    public static final String TEST_PRODUCT = "Test product";
    public static final String SKU = "199/2";

    private Product product;
    @Before
    public void setUp() {
        /*
        product = new Product();
        product.setName(TEST_PRODUCT);
        product.setSku(SKU);
        productRepository.save(product);

        ChunkItem item1 = new ChunkItem(new ChunkItemKey(1L, product.getId()));
        item1.setQuantity(20f);
        ChunkItem item2 = new ChunkItem(new ChunkItemKey(2L, product.getId()));
        item2.setQuantity(10f);
        chunkRepository.save(item1);
        chunkRepository.save(item2);
        product.addChunk(item1);
        product.addChunk(item2);
*/
    }

    @Test
    public void shouldSubmitAndCancelOrder() throws Exception, OrderSubmitException {
       /* Order order = new Order();

        ChunkItem one = chunkRepository.findOne(new ChunkItemKey(1L, product.getId()));
        ChunkItem two = chunkRepository.findOne(new ChunkItemKey(2L, product.getId()));

        order.addLine(new OrderLine(product, one, 1.1f));
        order.addLine(new OrderLine(product, two, 1.3f));
        orderProcessor.submitOrder(order);

        Assert.assertTrue( 18.9f == chunkRepository.findOne(new ChunkItemKey(1L,product.getId())).getQuantity());
        Assert.assertTrue( 8.7f == chunkRepository.findOne(new ChunkItemKey(2L,product.getId())).getQuantity());

        orderProcessor.cancelOrder(order.getId());

        order = orderService.findOrder(order.getId());
        Assert.assertTrue(order.getStatus() == Order.Status.CANCELED);
        Assert.assertTrue(1 == orderService.findCanceledOrders().size());

        Assert.assertTrue( 20f == chunkRepository.findOne(new ChunkItemKey(1L,product.getId())).getQuantity());
        Assert.assertTrue( 10f == chunkRepository.findOne(new ChunkItemKey(2L,product.getId())).getQuantity());*/

    }


}