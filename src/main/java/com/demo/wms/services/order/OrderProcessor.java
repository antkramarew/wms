package com.demo.wms.services.order;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.ChunkItemKey;
import com.demo.wms.domain.Order;
import com.demo.wms.domain.OrderLine;
import com.demo.wms.exeptions.ChunkOutOfStockException;
import com.demo.wms.exeptions.OrderSubmitException;
import com.demo.wms.repository.ChunkRepository;
import com.demo.wms.repository.OrderRepository;
import com.demo.wms.repository.ProductRepository;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
@SpringComponent
public class OrderProcessor {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChunkRepository chunkRepository;

    @Transactional
    public void submitOrder(Order order) throws OrderSubmitException {
        try {
            processLines(order);
        } catch (ChunkOutOfStockException ex) {
            throw new OrderSubmitException(ex);
        }
        persistOrder(order, Order.Status.COMPLETED);

    }
    @Transactional
    public void cancelOrder(Long orderId) {
        Order canceledOrder = orderRepository.findOne(orderId);
        canceledOrder.getLines().forEach(this::cancelOrderLine);
        persistOrder(canceledOrder, Order.Status.CANCELED);
    }

    private void persistOrder(Order order, Order.Status status) {
        order.setStatus(status);
        orderRepository.save(order);
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
        ChunkItem savedItem = chunkRepository.findOne(new ChunkItemKey(orderLine.getItem().getKey().getId(),
                orderLine.getProduct().getId()));
        if(orderLine.getQuantity() > savedItem.getQuantity() ){
            throw new ChunkOutOfStockException("Not enough quantity for chunk :" + savedItem.getKey().getId());
        }
        orderLine.getItem().setQuantity(savedItem.getQuantity() - orderLine.getQuantity());
    }


}
