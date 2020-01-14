package com.tc.corda.rpc.pool.impl;

import com.tc.corda.config.CordappConfig;
import com.tc.corda.rpc.pool.connection.CordaNodeRpcConnection;
import com.tc.corda.rpc.pool.factory.CordaRpcConnectionFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RpcConnectionPool extends AbstractCordaRpcConnectionPool implements InitializingBean {

   @Autowired
   CordappConfig cordappConfig;

    @Override
    public void afterPropertiesSet() {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(cordappConfig.getMaxConnection());
        conf.setMaxIdle(cordappConfig.getMaxIdel());
        pool = new GenericObjectPool<CordaNodeRpcConnection>(new CordaRpcConnectionFactory(cordappConfig.getUrl(), cordappConfig.getUser(), cordappConfig.getPwd()), conf);
    }
}
