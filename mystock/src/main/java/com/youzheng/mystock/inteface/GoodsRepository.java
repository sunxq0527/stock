package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.Y_Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Y_Goods,Long> {

    public Page<Y_Goods> findByState(Pageable pageable, String state);

    public Page<Y_Goods> findByStateAndName(Pageable pageable, String state,String name);

    public Y_Goods findById(Long id);

    public Y_Goods findByName(String name);

    public List<Y_Goods> findByState(String s);

}
