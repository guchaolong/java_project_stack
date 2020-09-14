package com.zeki.java_project_stack.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/14 16:32
 */
@Data
@Builder
public class Book {
    private String name;
    private String author;
    private BigDecimal price;
}
