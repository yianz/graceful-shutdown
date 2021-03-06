package com.jinpei.graceful.shutdown.springboot1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

/**
 * Created by liuzhaoming on 2020/12/21
 */
@RequestMapping("/api/hello")
@RestController
@Slf4j
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public Long hello(@RequestParam long serialNo,
                      @RequestParam(required = false, defaultValue = "0") int sleepSeconds) throws InterruptedException {
        log.info("Receive hello request {}", serialNo);
        if (sleepSeconds > 0) {
            Thread.sleep(sleepSeconds * 1000);
        }
        log.info("Finish hello request {}", serialNo);
        return serialNo;
    }

    @PreDestroy
    public void close() {
        log.info("*** Close is called!");
    }
}
