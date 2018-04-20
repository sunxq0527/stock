package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.C_Role;
import com.youzheng.mystock.entity.C_User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface UserRepository extends JpaRepository<C_User,Long> {

    public Page<C_User> findByState(Pageable pageable,String state);

    public Page<C_User> findByStateAndName(Pageable pageable,String state,String name);

    public List<C_User> findByIdIn(Collection ids);

    public C_User findById(Long id);
}
