package com.zeki.java_project_stack;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * @author
 */
@SpringBootApplication
@Order(3)//ApplicationRunner.run方法执行顺序
public class JavaProjectStackApplication implements ApplicationRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext con = null;
        con = SpringApplication.run(JavaProjectStackApplication.class, args);
        System.out.println("HE");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("容器启动完成");
    }
}
