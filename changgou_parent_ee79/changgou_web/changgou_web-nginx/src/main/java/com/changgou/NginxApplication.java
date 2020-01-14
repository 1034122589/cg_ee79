package com.changgou;
/*
 * @author April0ne
 * @date 2020/1/14 12:31
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableEurekaClient
public class NginxApplication {
    public static void main(String[] args) {
        SpringApplication.run(NginxApplication.class,args);
    }
}
