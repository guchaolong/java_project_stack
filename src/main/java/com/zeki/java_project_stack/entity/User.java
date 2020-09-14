package com.zeki.java_project_stack.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/14 16:32
 */
@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private List<Book> books;
}
