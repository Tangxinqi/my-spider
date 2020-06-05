package org.github.txq.spider.boot.starter.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangxinqi
 * @date 2020/4/5 17:36
 */
@SpringBootApplication(scanBasePackages = "org.github.txq.spider")
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

}
