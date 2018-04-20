package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.C_Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<C_Role,Long>{


    C_Role findById(long id);
    List<C_Role> findByPidAndIsValid(C_Role p,String s);

    List<C_Role> findByUsersId(Long id);

}
