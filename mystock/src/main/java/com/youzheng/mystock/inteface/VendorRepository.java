package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.Y_Goods;
import com.youzheng.mystock.entity.Y_Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Y_Vendor,Long> {

    public Page<Y_Vendor> findByState(Pageable pageable,String state);

    public Page<Y_Vendor> findByStateAndName(Pageable pageable,String state,String name);

    public Y_Vendor findById(Long id);

    public Y_Vendor findByName(String name);

    public List<Y_Vendor> findByGoodsAndState(Y_Goods goods, String state);

    public List<Y_Vendor> findByState(String state);
}
