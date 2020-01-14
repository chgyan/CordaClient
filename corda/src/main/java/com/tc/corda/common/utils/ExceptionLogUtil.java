package com.tc.corda.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogUtil.class);

    public static void logException(Exception e) {
        logException(logger, e);
    }

    public static void logException(Logger logger1, Exception e) {
        Logger log = logger1 == null ? ExceptionLogUtil.logger : logger1;
        log.error("localizaizedMessage : {}", e.getLocalizedMessage());
        log.error("exception message : {}", e.getMessage());
        log.error("exception cause : {}", e.getCause());
        //异常输出
        log.error("exception toString and track space : {}", "\r\n" + e);
        log.error(errorTrackSpace(e));
        log.error("---------------------------------------------");
        e.printStackTrace();
    }

    private static String errorTrackSpace(Exception e) {
        StringBuffer sb = new StringBuffer();
        if (e != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append("\r\n\t").append(element);
            }
        }
        return sb.length() == 0 ? null : sb.toString();
    }
}
