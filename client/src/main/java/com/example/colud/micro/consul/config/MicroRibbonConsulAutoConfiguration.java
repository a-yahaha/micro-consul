package com.example.colud.micro.consul.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author dyj
 * @description
 * @date created in 10:16 2019/3/15
 * @modify history
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnConsulEnabled
@ConditionalOnBean(SpringClientFactory.class)
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@ConditionalOnExpression("${spring.cloud.consul.ribbon.enabled}==false")
@RibbonClients(defaultConfiguration = MicroConsulRibbonClientConfiguration.class)
public class MicroRibbonConsulAutoConfiguration {
}
