package com.briup.env.until;

import java.io.FileNotFoundException;
import java.io.IOException;


public interface BackUP extends WossModule{
    void store(String filePath, Object obj,boolean append) ;//备份数据
    Object load(String filePath, boolean del) ;//读数据
}
