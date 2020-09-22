package com.itheima.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author : qiangshengchen
 * @date : 上午 10:34 21/9/2020
 */
public class JobApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:ApplicationContext-Job.xml");

        System.in.read();


    }

}
