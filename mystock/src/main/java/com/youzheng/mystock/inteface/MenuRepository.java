package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.C_Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends JpaRepository<C_Menu,Long> {


    C_Menu findById(long id);

    List<C_Menu> findByPid(C_Menu p);

    List<C_Menu> findByIdIn(List<Long> menu);

    List<C_Menu> findByRolesIdIn(Collection ids);
}
