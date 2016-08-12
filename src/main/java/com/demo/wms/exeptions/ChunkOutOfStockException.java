package com.demo.wms.exeptions;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
public class ChunkOutOfStockException extends Throwable {
    public ChunkOutOfStockException(String s) {
        super(s);
    }
}
