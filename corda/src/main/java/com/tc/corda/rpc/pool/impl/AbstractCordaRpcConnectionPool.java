package com.tc.corda.rpc.pool.impl;

import com.tc.corda.common.utils.ExceptionLogUtil;
import com.tc.corda.rpc.RpcParamMap;
import com.tc.corda.rpc.pool.IConnectionPool;
import com.tc.corda.rpc.pool.connection.CordaNodeRpcConnection;
import net.corda.client.rpc.RPCException;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.Map;
import java.util.function.Function;

public abstract class AbstractCordaRpcConnectionPool implements IConnectionPool, DisposableBean {
    Logger logger = LoggerFactory.getLogger(AbstractCordaRpcConnectionPool.class);

    GenericObjectPool<CordaNodeRpcConnection> pool;

    public Object call(Function f, Map<String, Object> paramsMap) {
        CordaNodeRpcConnection con = null;
        try {
            con = pool.borrowObject();
            RpcParamMap map = new RpcParamMap(con.getProxy());
            if (paramsMap != null) {
                map.putAll(paramsMap);
            }
            return f.apply(map);
        } catch (RPCException e) {
            if (con != null) {
                try {
                    con.close(); //设定为关闭，会再次连接
                } catch (Exception el) {
                    ExceptionLogUtil.logException(logger, el);
                }
            }
        } catch (Exception ex) {
            ExceptionLogUtil.logException(logger, ex);
        } finally {
            try {
                if (null != con) {
                    pool.returnObject(con);
                }
            } catch (Exception el) {
                // ignored
            }
        }

        return null;
    }

    @Override
    public void destroy() throws Exception {
        pool.clear();
    }
}
