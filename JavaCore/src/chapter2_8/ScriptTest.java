package chapter2_8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ScriptTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            try {
                ScriptEngineManager manager = new ScriptEngineManager();
                String language = "js";

                final ScriptEngine engine = manager.getEngineByName(language);

                if(engine == null) {
                    System.err.println("No engine for " + language);
                    System.exit(1);
                }

                final String frameClassName = "chapter2_8.ButtonFrame";
                JFrame frame = (JFrame) Class.forName(frameClassName).newInstance();
                InputStream in = frame.getClass().getResourceAsStream("init." + language);
                if(in != null) {
                    engine.eval(new InputStreamReader(in));
                }

                Map<String, Component> components = new HashMap<>();
                getComponentBindings(frame, components);

                components.forEach((name, c) -> engine.put(name, c));

                components.forEach((name, c) -> System.out.println("Component :" + name));
                System.out.println(String.join("", Collections.nCopies(20, "=")));

                final Properties events = new Properties();
                in = frame.getClass().getResourceAsStream(language + ".properties");
                events.load(in);

                for(final Object e: events.keySet()) {
                    String[] s = ((String) e).split("\\.");
                    String scriptCode = (String) events.get(e);
                    addListener(s[0], s[1], scriptCode, engine, components);

                    System.out.println("Code: " + scriptCode);
                }
                System.out.println(String.join("", Collections.nCopies(20, "=")));

                frame.setTitle("ScriptTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

            } catch (ReflectiveOperationException | ScriptException | IOException | IntrospectionException ex) {
                ex.printStackTrace();
            }
        });
    } // main()

    private static void addListener(String beanName,
                                    String eventName,
                                    final String scriptCode,
                                    ScriptEngine engine,
                                    Map<String, Component> components)
    throws ReflectiveOperationException, IntrospectionException
    {
        Object bean = components.get(beanName);
        EventSetDescriptor descriptor = getEventSetDescriptor(bean, eventName);
        if (descriptor == null) return;
        descriptor.getAddListenerMethod().invoke(bean, Proxy.newProxyInstance(null, new Class[] {
            descriptor.getListenerType()},
                (proxy, method, args) -> {
                    engine.eval(scriptCode);
                    return null;
                }
        ));
    }

    private static EventSetDescriptor getEventSetDescriptor(Object bean, String eventName)
            throws IntrospectionException
    {
        for (EventSetDescriptor descriptor: Introspector.getBeanInfo(bean.getClass()).getEventSetDescriptors()) {

            if (descriptor.getName().equals(eventName)) {
                System.out.println("\t Descriptor name: " + descriptor.getName());
                System.out.println("\t\t getListenerType: " + descriptor.getListenerType());
                return descriptor;
            }
//            else {
//                System.out.println("\t Filtered descriptor: " + descriptor.getName());
//            }
        }
        return null;
    }

    private static void getComponentBindings(Component c, Map<String, Component> components) {
        String name = c.getName();
        if(name != null) components.put(name, c);
        if(c instanceof Container) {
            for(Component child: ((Container) c).getComponents()) {
                getComponentBindings(child, components);
            }
        }

    } // ** getComponentBindings()

} // class ScriptTest
