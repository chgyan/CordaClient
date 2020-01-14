package com.tc.corda;

import com.tc.corda.common.utils.RandomUtils;
import com.tc.corda.service.CordaRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude =
        {org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration.class,
                org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.class})
@RestController
public class CordaApplication {

    Logger logger = LoggerFactory.getLogger(CordaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CordaApplication.class, args);
    }

    @Autowired
    CordaRpcService service;

    @GetMapping("/network")
    public String networkTest() {
        return service.getNetworkMap();
    }

    @Autowired
    ThreadPoolTaskExecutor executor;

    @GetMapping("/multi-conn")
    public String multiConn(@RequestParam(value = "n", required = false, defaultValue = "3")int n) {
        for(int i=0;i<n;i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(RandomUtils.nextInt(1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    logger.info(service.getNetworkMap());
                }
            });
        }
        return "1";
    }
}
