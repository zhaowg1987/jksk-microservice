package com.jksk.orgcreditratingcon;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.jksk")
public class OrgCreditRatingConApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgCreditRatingConApplication.class, args);
    }

}
