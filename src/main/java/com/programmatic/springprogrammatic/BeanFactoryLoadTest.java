package com.programmatic.springprogrammatic;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @title: BeanFactoryLoadTest
 * @description:
 * @author: zhangfan
 * @data: 2018年05月19日 14:28
 */
public class BeanFactoryLoadTest {

    public static void main(String[] args) {

        ClassPathResource resource = new ClassPathResource("bean_factory_load.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);

        BeanFactoryLoadTest beanFactoryLoadTest = factory.getBean(BeanFactoryLoadTest.class);
        beanFactoryLoadTest.print();

    }

    public void print() {
        System.out.println("this is factory load test");
    }

}
