package com.microservice.eureka_server;


import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;

public class EurekaServerApplicationTests {


    @MockBean
    private CloudEurekaClient cloudEurekaClient;

    @MockBean
    private EurekaRegistration eurekaRegistration;

    @Test
    public void contextLoads() {
        // Your test logic here
    }

}
