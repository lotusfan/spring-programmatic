package com.programmatic.springprogrammatic;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @title: ProxyTest
 * @description:
 * @author: zhangfan
 * @data: 2018年05月23日 14:44
 */
public class ProxyTest {


    public static void main(String[] args) throws Exception {

        proxyJdk();
//        printJDKProxyClassByte();

//        proxyCglib();
    }


    public static void proxyJdk() {

        ProxyInstanceJDK proxyInstance = (ProxyInstanceJDK) Proxy.newProxyInstance(ProxyInstanceJDKImpl.class.getClassLoader(),
                ProxyInstanceJDKImpl.class.getInterfaces(), new ProxyHandlerJDK(new ProxyInstanceJDKImpl()));

        proxyInstance.print();
    }

    /**
     * 可以将生成好的代理类字节码打印出来
     * @throws Exception
     */
    public static void printJDKProxyClassByte() throws Exception {

        byte[] classFile = ProxyGenerator.generateProxyClass("$ProxyMyTest", ProxyInstanceJDKImpl.class.getInterfaces());

        FileOutputStream fos = new FileOutputStream(new File("/Users/zhangfan/IdeaProjects/springboot-project/spring-programmatic/src/main/resources/example.class"));

        fos.write(classFile);
        fos.flush();
        fos.close();
    }


    public static void proxyCglib() {
        //将生成代理类的字节码打印出来
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/zhangfan/IdeaProjects/springboot-project/spring-programmatic/src/main/resources/templates");


        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(ProxyInstanceCglib.class);
        enhancer.setCallback(((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("enter the cglib proxy invoke");
            return methodProxy.invokeSuper(o, objects);
        }));

        ProxyInstanceCglib instanceCglib = (ProxyInstanceCglib) enhancer.create();
        instanceCglib.print();
        System.out.println(instanceCglib.getClass().getSimpleName());

    }


}


class ProxyInstanceCglib {
    public void print() {
        System.out.println("target cglib class");
    }
}


interface ProxyInstanceJDK {

    void print();
}

class ProxyInstanceJDKImpl implements ProxyInstanceJDK {

    @Override
    public void print() {
        System.out.println("target jdk class");
    }

}

class ProxyHandlerJDK implements InvocationHandler {

    private ProxyInstanceJDK targetClass;

    public ProxyHandlerJDK(ProxyInstanceJDK targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("enter the jdk proxy invoke, proxy className is " + proxy.getClass().getSimpleName());

        return method.invoke(targetClass, args);
    }
}
