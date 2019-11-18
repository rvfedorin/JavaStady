/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.singleton;

/**
 *
 * @author Wolf
 */
public class Singleton {
    private static Singleton instSingleton;
    
    private Singleton() {
    }
    
    public static Singleton getInstance() {
        if(instSingleton == null) {
            synchronized(Singleton.class) {
                if(instSingleton == null) {
                    instSingleton = new Singleton();
                }
            }
        }
        
      return instSingleton;  
    }
}
