package com.tc.corda.service;

import com.tc.corda.common.utils.ExceptionLogUtil;
import com.tc.corda.rpc.RpcParamMap;
import com.tc.corda.rpc.pool.IConnectionPool;
import net.corda.client.rpc.RPCException;
import net.corda.core.node.NetworkParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CordaRpcService {

    private static final Logger logger = LoggerFactory.getLogger(CordaRpcService.class);

    @Autowired
    IConnectionPool pool;

    public String getNetworkMap() {
        return (String)pool.call(this::_networkMap, null);
    }

    private Object _networkMap(Object object) {
        RpcParamMap param = (RpcParamMap) object;
        try {
            NetworkParameters o = param.getProxy().getNetworkParameters();
            return o.toString();
        } catch (RPCException e) {
            logger.error(e.getMessage());
        } catch (Exception ex) {
            ExceptionLogUtil.logException(logger, ex);
        }
        return "";
    }
}
