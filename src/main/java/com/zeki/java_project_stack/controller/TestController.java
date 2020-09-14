package com.zeki.java_project_stack.controller;

import com.zeki.java_project_stack.entity.Book;
import com.zeki.java_project_stack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/12 11:03
 */
@RestController
public class TestController<T> {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String test() {
        return "hello world";
    }


    @GetMapping("/redis/putUserData")
    public void putUserData() {
        List<Book> books = new ArrayList<>();
        Book b1 = Book.builder().author("au1").name("计算机科学").price(new BigDecimal(20)).build();
        Book b2 = Book.builder().author("au2").name("c++").price(new BigDecimal(40)).build();
        books.add(b1);
        books.add(b2);
        User user1 = User.builder().id(1234).name("user1").books(books).build();

        //String
        stringRedisTemplate.opsForValue().set("test1", "100", 60 * 10, TimeUnit.SECONDS);//操作字符串
        String test1 = stringRedisTemplate.opsForValue().get("test1");
        System.out.println(test1);
        Long test11 = stringRedisTemplate.boundValueOps("test1").increment();
        System.out.println(test11);


        //Hash
        stringRedisTemplate.opsForHash().put("hashValue","map1","map1-1");
        stringRedisTemplate.opsForHash().put("hashValue","map2","map2-2");
        List<Object> hashList = stringRedisTemplate.opsForHash().values("hashValue");
        System.out.println("通过values(H key)方法获取变量中的hashMap值:" + hashList);

        Map<Object,Object> map = stringRedisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过entries(H key)方法获取变量中的键值对:" + map);

        Object mapValue = stringRedisTemplate.opsForHash().get("hashValue","map1");
        System.out.println("通过get(H key, Object hashKey)方法获取map键的值:" + mapValue);

        boolean hashKeyBoolean = stringRedisTemplate.opsForHash().hasKey("hashValue","map3");
        System.out.println("通过hasKey(H key, Object hashKey)方法判断变量中是否存在map键:" + hashKeyBoolean);

        Set<Object> keySet = stringRedisTemplate.opsForHash().keys("hashValue");
        System.out.println("通过keys(H key)方法获取变量中的键:" + keySet);

        long hashLength = stringRedisTemplate.opsForHash().size("hashValue");
        System.out.println("通过size(H key)方法获取变量的长度:" + hashLength);
//        stringRedisTemplate.opsForList();　　 //操作list
//        stringRedisTemplate.opsForSet();　　  //操作set
//        stringRedisTemplate.opsForZSet();　 　//操作有序set

    }


}
