package com.zsy.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName VodApplication
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/9 12:10 PM
 * @Version 1.0
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.zsy"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class);
    }
}
