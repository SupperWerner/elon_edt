package com.zsy.crm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName SpringConfig
 * @Description Spring配置类
 * @Author mybook
 * @Date 2020/5/16 4:59 PM
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.zsy.crm.mapper")
public class SpringConfig {
}
