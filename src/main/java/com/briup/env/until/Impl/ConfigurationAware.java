package com.briup.env.until.Impl;

import com.briup.env.until.Configuration;


public interface ConfigurationAware  {
    void setConfiguration(Configuration conf);//让各个子模块实现该接口，注入需要的模块对象

}
