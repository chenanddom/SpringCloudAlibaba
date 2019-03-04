package com.nacos.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class DemoController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/getInformation")
    public String getInfo(String param) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider-demo");
        URI uri = serviceInstance.getUri();
        System.out.println("param="+param+"----"+"url="+uri);
        String serviceId = serviceInstance.getServiceId();
        System.out.println("serviceId:"+serviceId);
        return restTemplate.getForObject("http://nacos-provider-demo:8061/getInfo?parameter="+param, String.class);
    }

}
