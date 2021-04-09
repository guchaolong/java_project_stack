package com.zeki.globalexceptionhandle.service.impl;

import com.zeki.globalexceptionhandle.exception.ParamException;
import com.zeki.globalexceptionhandle.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author GUCHAOLONG
 * @date 2021/4/9 5:56
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String biz() {
        throw new ParamException("param error");
    }
}
