package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient  //开启Eureka客户端服务发现
//配置通用Mapper包扫描
@MapperScan(basePackages = "com.changgou.evaluate.dao")
public class EvaluateApplication {
    public static void main(String[] args) {
        SpringApplication.run(EvaluateApplication.class,args);
    }
}
