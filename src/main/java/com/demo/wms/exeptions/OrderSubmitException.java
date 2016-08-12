package com.demo.wms.exeptions;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
public class OrderSubmitException extends Throwable {

    public OrderSubmitException(ChunkOutOfStockException ex) {
        super(ex);
    }
    public OrderSubmitException(String message) {
        super(message);
    }
}
