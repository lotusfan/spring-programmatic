package com.programmatic.springprogrammatic;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @title: ApplicationContextLoadTest
 * @description:
 * @author: zhangfan
 * @data: 2018年05月21日 14:41
 */
public class ApplicationContextLoadTest {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application_context_load.xml");
        applicationContext.getBean(ApplicationContextLoadTest.class).print();

    }

    public void print() {
        System.out.println("this is ApplicationContextLoadTest");
    }
}
