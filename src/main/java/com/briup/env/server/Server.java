package com.briup.env.server;

import com.briup.env.bean.Environment;
import com.briup.env.until.WossModule;

import java.util.Collection;


//接受
public interface Server extends WossModule {
    Collection<Environment> revicer();//接受模块
    void shutdown();//关闭模块
}
