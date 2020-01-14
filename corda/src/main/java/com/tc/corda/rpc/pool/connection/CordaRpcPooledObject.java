package com.tc.corda.rpc.pool.connection;

import com.tc.corda.common.utils.ExceptionLogUtil;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CordaRpcPooledObject extends DefaultPooledObject<CordaNodeRpcConnection> {
    Logger logger = LoggerFactory.getLogger(CordaRpcPooledObject.class);

    public CordaRpcPooledObject(CordaNodeRpcConnection object) {
        super(object);
    }

    @Override
    public synchronized void invalidate() {
        try {
            getObject().close();
        } catch (Exception e) {
            ExceptionLogUtil.logException(logger, e);
        }
        super.invalidate();
    }
}
