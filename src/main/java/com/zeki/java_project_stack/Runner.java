package com.zeki.java_project_stack;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
 */
@Component
public class Runner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] sourceArgs = args.getSourceArgs();
        System.out.println(sourceArgs);
        System.out.println("容器启动后执行");
    }
}
