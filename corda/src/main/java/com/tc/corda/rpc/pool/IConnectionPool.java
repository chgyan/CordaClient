package com.tc.corda.rpc.pool;

import java.util.Map;
import java.util.function.Function;

public interface IConnectionPool {

    public Object call(Function f, Map<String, Object> paramsMap);
}
