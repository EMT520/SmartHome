package com.briup.env.until;

import java.util.Properties;


public interface WossModule {
    /**
     * @method 各个模块初始化的方法
     * @param1  参数配置对象
     */
    void init(Properties p);
}
