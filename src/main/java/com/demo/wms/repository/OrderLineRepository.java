package com.demo.wms.repository;

import com.demo.wms.domain.OrderLine;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
}
