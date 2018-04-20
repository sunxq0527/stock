package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.Y_Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeachRepository extends JpaRepository<Y_Stock,Long>,JpaSpecificationExecutor<Y_Stock> {
}
