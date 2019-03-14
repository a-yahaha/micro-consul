package com.example.colud.micro.consul.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author dyj
 * @description
 * @date created in 16:16 2019/3/14
 * @modify history
 */
@FeignClient(name = "micro-consul")
public interface MicroConsulClient {

    @RequestMapping(value = "/hello", method = GET)
    String hello();
}
