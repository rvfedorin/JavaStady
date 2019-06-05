package chapter1_6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];

        for(int i=0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            Object proxy = Proxy.newProxyInstance(null, new Class[] {Comparable.class}, handler);
            elements[i] = proxy;
        } // close for()

        Integer key = new Random().nextInt(elements.length) + 1;
        int result = Arrays.binarySearch(elements, key);
        if(result >= 0) System.out.println(result);

    } // close main(String[] args)
} // close class ProxyTest

class TraceHandler implements InvocationHandler {
    private Object target;

    TraceHandler(Object val) {
        target = val;
    } // close TraceHandler(Integer val)

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(target);
        System.out.print('.' + method.getName() + "(");
        if (args != null) {
            for(int i=0; i<args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(", ");
            } // close for()
            System.out.println(")");
        } // close if()

        return method.invoke(target, args);
    } // close invoke()
} // close class TraceHandler
