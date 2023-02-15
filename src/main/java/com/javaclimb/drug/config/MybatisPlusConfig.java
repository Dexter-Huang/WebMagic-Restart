package com.javaclimb.drug.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import icu.mhb.mybatisplus.plugln.injector.JoinDefaultSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MybatisPlus配置类
 */
@Configuration
@MapperScan(value = "com.javaclimb.drug.mapper")
public class MybatisPlusConfig extends JoinDefaultSqlInjector {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 自己的自定义方法
        return methodList;
    }
}
