package com.tc.corda.rpc;

import net.corda.core.messaging.CordaRPCOps;

import java.util.HashMap;

public class RpcParamMap extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private static final String PROXY_NAME = "rpc_node_proxy";

    public RpcParamMap(CordaRPCOps proxy) {
        put(PROXY_NAME, proxy);
    }

    public void addProxy(CordaRPCOps proxy) {
        put(PROXY_NAME, proxy);
    }

    public CordaRPCOps getProxy() {
        return (CordaRPCOps) get(PROXY_NAME);
    }

}
