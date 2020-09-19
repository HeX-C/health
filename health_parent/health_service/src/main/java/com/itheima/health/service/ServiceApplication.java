package com.itheima.health.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : qiangshengchen
 * @date : 下午 8:22 14/9/2020
 */
public class ServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring_service.xml");
        System.in.read();


    }
}
