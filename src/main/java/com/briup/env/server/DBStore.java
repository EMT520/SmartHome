package com.briup.env.server;

import com.briup.env.bean.Environment;
import com.briup.env.until.WossModule;

import java.io.IOException;
import java.util.Collection;


public interface DBStore extends WossModule {
    void saveDB(Collection<Environment> coll) throws IOException;
}
