package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.Y_Stock;
import org.springframework.data.repository.CrudRepository;

public interface BatchStock extends CrudRepository<Y_Stock,Long> {
}
