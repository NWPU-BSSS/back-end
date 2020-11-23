package com.nwpu.bsss;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ForestScan(basePackages = "com.nwpu.bsss.utils")
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
public class BsssApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsssApplication.class, args);
    }

}
