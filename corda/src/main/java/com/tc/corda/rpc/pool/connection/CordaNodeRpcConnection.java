package com.tc.corda.rpc.pool.connection;

import com.tc.corda.common.utils.ExceptionLogUtil;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CordaNodeRpcConnection implements AutoCloseable {

    Logger logger = LoggerFactory.getLogger(CordaNodeRpcConnection.class);

    private final String url;
    private final String user;
    private final String password;

    private CordaRPCConnection connection = null;
    private CordaRPCOps proxy = null;

    public CordaNodeRpcConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        connect();
    }

    private void connect() {
        logger.info("开始连接 {}", url);
        try {
            NetworkHostAndPort nodeAddress = NetworkHostAndPort.parse(url);
            CordaRPCClient client = new CordaRPCClient(nodeAddress);
            connection = client.start(user, password);
            proxy = connection.getProxy();
        } catch (Exception e) {
            ExceptionLogUtil.logException(logger, e);
            try {
                close(); //如果出现异常则关闭
            } catch (Exception el) {
                ExceptionLogUtil.logException(logger, el);
            }
        }
    }

    @Override
    public void close() throws Exception {

        if (connection != null) {
            connection.notifyServerAndClose();
            connection = null;
        }
        if (proxy != null) {
            proxy = null;
        }

        logger.info("关闭连接 {}", url);
    }

    //TODO 是否要定时来判定连接是否有效
//    public void ping(){
//        try {
//            proxy.getNetworkParameters(); //获取网络拓扑来判定proxy是否有效
//        }catch (RPCException e){
//            try {
//                close();
//                connect();
//            }catch (Exception el){
//                ExceptionLogUtil.logException(logger, el);
//            }
//        }
//    }

    public CordaRPCOps getProxy() {
        if (proxy == null) {
            connect();
        }
        return proxy;
    }
}
