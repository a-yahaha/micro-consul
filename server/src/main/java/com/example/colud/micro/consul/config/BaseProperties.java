package com.example.colud.micro.consul.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dyj
 * @description
 * @date created in 20:58 2019/3/13
 * @modify history
 */

@ConfigurationProperties
@Component
@Data
public class BaseProperties {

    @Value("${micro.service.token:default}")
    private String token;
}
