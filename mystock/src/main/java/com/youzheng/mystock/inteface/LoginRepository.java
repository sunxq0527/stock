package com.youzheng.mystock.inteface;

import com.youzheng.mystock.entity.C_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends JpaRepository<C_User,Long> {
    C_User findByUsername(String username);

    @Query(value="select * from c_user where c_user.username=:username and c_user.password=:password",nativeQuery = true)
    C_User findByUsername(@Param("username")String username, @Param("password")String password);
}
