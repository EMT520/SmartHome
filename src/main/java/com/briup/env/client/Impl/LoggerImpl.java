package com.briup.env.client.Impl;

import com.briup.env.until.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;


public class LoggerImpl implements Logger {
    private org.apache.log4j.Logger log
            =org.apache.log4j.Logger.getRootLogger();



    @Override
    public void init(Properties p) {
        PropertyConfigurator
                .configure(p.getProperty("logPath"));
    }
    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        log.debug(e);
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String msg, Throwable e) {
        log.info(e);
    }

    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable e) {
        log.warn(e);
    }

    @Override
    public void error(String msg) {
        log.error(msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        log.error(e);
    }

    @Override
    public void fatal(String msg) {
        log.fatal(msg);
    }

    @Override
    public void fatal(String msg, Throwable e) {
        log.fatal(e);
    }
}
