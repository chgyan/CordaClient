package com.tc.corda.rpc.pool.factory;

import com.tc.corda.rpc.pool.connection.CordaNodeRpcConnection;
import com.tc.corda.rpc.pool.connection.CordaRpcPooledObject;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

public class CordaRpcConnectionFactory extends BasePooledObjectFactory<CordaNodeRpcConnection> {

    private final String url;
    private final String user;
    private final String password;

    public CordaRpcConnectionFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public CordaNodeRpcConnection create() throws Exception {
        return new CordaNodeRpcConnection(url, user, password);
    }

    @Override
    public PooledObject<CordaNodeRpcConnection> wrap(CordaNodeRpcConnection cordaNodeRpcConnection) {
        return new CordaRpcPooledObject(cordaNodeRpcConnection);
    }
}
