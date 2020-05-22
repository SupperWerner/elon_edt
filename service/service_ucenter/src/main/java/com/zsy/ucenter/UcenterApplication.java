package com.zsy.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName UcenterApplication
 * @Description 启动类
 * @Author mybook
 * @Date 2020/5/21 3:03 PM
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan("com.zsy")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class);
    }
}
