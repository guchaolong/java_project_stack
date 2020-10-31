package com.zeki.apiresponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiResponseApplication {

    public static void main(String[] args) {
        ResultCode.SUCCESS.code();
        SpringApplication.run(ApiResponseApplication.class, args);
    }

}
