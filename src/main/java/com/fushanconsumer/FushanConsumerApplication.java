package com.fushanconsumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration //dubbo支持注解
public class FushanConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FushanConsumerApplication.class, args);
        System.out.println("....");
    }

}
