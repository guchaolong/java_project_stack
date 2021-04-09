package com.zeki.java_project_stack.do_something_when_start_complete;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/12 10:40
 */

/**
 *在开发中可能会有这样的情景。需要在容器启动的时候执行一些内容。比如读取配置文件，数据库连接之类的。
 * SpringBoot给我们提供了两个接口来帮助我们实现这种需求。这两个接口分别为CommandLineRunner和ApplicationRunner。
 * 他们的执行时机为容器启动完成的时候。
 *
 * 这两个接口中有一个run方法，我们只需要实现这个方法即可。
 * 这两个接口的不同之处在于：ApplicationRunner中run方法的参数为ApplicationArguments，
 * 而CommandLineRunner接口中run方法的参数为String数组
 *
 * 原理
 * 启动的run方法中会去调用SpringApplication callRunners（）方法
 * 查找实现了ApplicationRunner和CommandLineRunner接口的Bean，统一存放在一个list中
 * 根据Bean的order进行排序
 * 循环调用每一个Runner Bean的run接口。
 *
 *
 *
 */
@Component
@Order(2)//ApplicationRunner.run方法执行顺序
public class MyRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] sourceArgs = args.getSourceArgs();
        System.out.println(sourceArgs);
        System.out.println("容器启动后执行");
    }
}
