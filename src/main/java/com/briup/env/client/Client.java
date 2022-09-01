package com.briup.env.client;

import com.briup.env.bean.Environment;
import com.briup.env.until.WossModule;

import java.io.IOException;
import java.util.Collection;


    public interface Client extends WossModule {
        void send(Collection<Environment> c) throws IOException;
    }
