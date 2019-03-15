package com.example.colud.micro.consul.config;

import com.ecwid.consul.v1.ConsulClient;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulPing;
import org.springframework.cloud.consul.discovery.ConsulServerIntrospector;
import org.springframework.cloud.consul.discovery.HealthServiceServerListFilter;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

import static com.netflix.client.config.CommonClientConfigKey.DeploymentContextBasedVipAddresses;
import static com.netflix.client.config.CommonClientConfigKey.EnableZoneAffinity;

/**
 * @author dyj
 * @description 注意不要使用@Configuration注解，让compscan扫描到
 * @date created in 21:32 2019/3/14
 * @modify history
 */
//@Configuration
public class MicroConsulRibbonClientConfiguration {

    protected static final String VALUE_NOT_SET = "__not__set__";

    protected static final String DEFAULT_NAMESPACE = "ribbon";

    @Autowired
    private ConsulClient client;

    private String serviceId = "client";

    public MicroConsulRibbonClientConfiguration() {
    }

    public MicroConsulRibbonClientConfiguration(String serviceId) {
        this.serviceId = serviceId;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerList<?> ribbonServerList(IClientConfig config,
                                          ConsulDiscoveryProperties properties) {
        MicroConsulServerList serverList = new MicroConsulServerList(this.client, properties);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerListFilter<Server> ribbonServerListFilter() {
        return new HealthServiceServerListFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public IPing ribbonPing() {
        return new ConsulPing();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsulServerIntrospector serverIntrospector() {
        return new ConsulServerIntrospector();
    }

    @PostConstruct
    public void preprocess() {
        setProp(this.serviceId, DeploymentContextBasedVipAddresses.key(), this.serviceId);
        setProp(this.serviceId, EnableZoneAffinity.key(), "true");
    }

    protected void setProp(String serviceId, String suffix, String value) {
        // how to set the namespace properly?
        String key = getKey(serviceId, suffix);
        DynamicStringProperty property = getProperty(key);
        if (property.get().equals(VALUE_NOT_SET)) {
            ConfigurationManager.getConfigInstance().setProperty(key, value);
        }
    }

    protected DynamicStringProperty getProperty(String key) {
        return DynamicPropertyFactory.getInstance().getStringProperty(key, VALUE_NOT_SET);
    }

    protected String getKey(String serviceId, String suffix) {
        return serviceId + "." + DEFAULT_NAMESPACE + "." + suffix;
    }


}
