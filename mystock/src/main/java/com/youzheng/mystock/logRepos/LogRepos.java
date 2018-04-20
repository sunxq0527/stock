package com.youzheng.mystock.logRepos;

import com.youzheng.mystock.entity.C_Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepos extends JpaRepository<C_Log,Long> {
}
