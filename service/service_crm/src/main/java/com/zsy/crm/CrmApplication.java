package com.zsy.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName CrmApplication
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/16 9:02 PM
 * @Version 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zsy"})
public class CrmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class,args);
    }
}
