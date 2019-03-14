package com.example.cloud.micro.test;

import com.example.colud.micro.consul.MicroConsulMicroService;
import com.example.colud.micro.consul.client.MicroConsulClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dyj
 * @description
 * @date created in 16:31 2019/3/14
 * @modify history
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroConsulMicroService.class)
public class FeignClientTest {

    @Autowired
    private MicroConsulClient microConsulClient;

    @Test
    public void testClient() {
        for (int i = 0; i < 10; i ++) {
            System.out.println(microConsulClient.hello());
        }
    }
}
