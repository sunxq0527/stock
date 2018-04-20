package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.Y_Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StockRepository extends JpaRepository<Y_Stock,Long>,JpaSpecificationExecutor<Y_Stock> {




}
