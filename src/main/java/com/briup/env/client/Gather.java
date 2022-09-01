package com.briup.env.client;

import com.briup.env.bean.Environment;
import com.briup.env.until.WossModule;

import java.util.Collection;


public interface Gather extends WossModule {
    Collection<Environment> gather();//采集数据方法
}
