package com.briup.env.until;

import com.briup.env.client.Gather;
import com.briup.env.server.DBStore;


//日志模块
public interface Logger extends WossModule{
    void debug(String msg);//调试输出，测试阶段
    void debug(String msg,Throwable e);//调试输出，测试阶段
    void info(String msg);//正常输出，生产记录业务流畅
    void info(String msg,Throwable e);//正常输出，生产记录业务流畅
    void warn(String msg);//警告输出，catch语句中
    void warn(String msg,Throwable e);//警告输出，catch语句中
    void error(String msg);//错误数据，系统错误，JVM退出
    void error(String msg,Throwable e);//错误数据，系统错误，JVM退出
    void fatal(String msg);
    void fatal(String msg,Throwable e);
}
