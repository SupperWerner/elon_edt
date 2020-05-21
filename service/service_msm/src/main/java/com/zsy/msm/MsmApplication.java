package com.zsy.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName MSMApplication
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/20 9:46 PM
 * @Version 1.0
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.zsy")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }
}
