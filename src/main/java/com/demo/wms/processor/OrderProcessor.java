package com.demo.wms.processor;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.ChunkItemKey;
import com.demo.wms.domain.Order;
import com.demo.wms.domain.OrderLine;
import com.demo.wms.exeptions.ChunkOutOfStockException;
import com.demo.wms.exeptions.OrderSubmitException;
import com.demo.wms.repository.ChunkRepository;
import com.demo.wms.repository.OrderRepository;
import com.demo.wms.repository.ProductRepository;
import com.demo.wms.services.OrderService;
import com.demo.wms.services.ProductService;
import com.demo.wms.util.CurrentUser;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@SpringComponent
public class OrderProcessor {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    public void submitOrder(Order order) throws OrderSubmitException {
        try {
            order.setSubmittedDate(new Date());
            order.setManager(CurrentUser.get());
            processLines(order);
        } catch (ChunkOutOfStockException ex) {
            throw new OrderSubmitException(ex);
        }
        orderService.saveOrderWithStatus(order, Order.Status.COMPLETED);

    }
    @Transactional
    public void cancelOrder(Long orderId) {
        Order canceledOrder = orderService.findOrder(orderId);
        canceledOrder.getLines().forEach(this::cancelOrderLine);
        orderService.saveOrderWithStatus(canceledOrder, Order.Status.CANCELED);
    }

    private void cancelOrderLine(OrderLine orderLine) {
        float lineQuantity = orderLine.getQuantity();
        ChunkItem orderItem = orderLine.getItem();
        orderItem.setQuantity(lineQuantity  + orderItem.getQuantity());
    }

    private void processLines(Order order) throws ChunkOutOfStockException {
        for (OrderLine orderLine : order.getLines()) {
            updateChunk(orderLine);
        }
    }

    private void updateChunk(OrderLine orderLine) throws ChunkOutOfStockException {
        ChunkItem itemToUpdate = productService.findChunk(orderLine.getItem().getId(), orderLine.getProduct().getId());
        if(orderLine.getQuantity() > itemToUpdate.getQuantity() ){
            throw new ChunkOutOfStockException("Not enough quantity for chunk :" + itemToUpdate.getId());
        }
        itemToUpdate.setQuantity(itemToUpdate.getQuantity() - orderLine.getQuantity());
        if(itemToUpdate.getQuantity()<=0){
            productService.deleteChunk(itemToUpdate);
        }
        else {
            productService.saveChunk(itemToUpdate);
        }
    }


}
