package com.demo.wms.repository;

import com.demo.wms.domain.ChunkItem;
import com.demo.wms.domain.ChunkItemKey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by anton_kramarev on 8/12/2016.
 */
public interface ChunkRepository extends CrudRepository<ChunkItem, ChunkItemKey> {
}
