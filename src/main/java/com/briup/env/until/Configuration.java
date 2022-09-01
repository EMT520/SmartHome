package com.briup.env.until;

import com.briup.env.client.Client;
import com.briup.env.client.Gather;
import com.briup.env.server.DBStore;
import com.briup.env.server.Server;


public interface Configuration {
    Logger getLogger();
    BackUP getBackup();
    Gather getGather();
    Client getClient();
    Server getServer();
    DBStore getDBStore();
}
