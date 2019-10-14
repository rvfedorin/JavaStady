/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.treeModel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 *
 * @author Wolf
 */
class Variable {
    private Class<?> type;
    private String name;
    private Object value;
    private ArrayList<Field> fields;

    Variable(Class<?> aType, String aName, Object aValue) {
        this.type = aType;
        this.name = aName;
        this.value = aValue;
        fields = new ArrayList<>();
        
        if(!type.isPrimitive() && !type.isArray() && !type.equals(String.class) && value !=null) {
            // get fields from the class and all superclasses
            for(Class<?> c = value.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fs = c.getDeclaredFields();
                AccessibleObject.setAccessible(fs, true);
                
                for(Field f: fs) {
                    if((f.getModifiers() & Modifier.STATIC) == 0) fields.add(f);
                }
            }
        }
    } // ** constructor

    ArrayList<Field> getFields() {
        return fields;
    }

    Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        String s = type + " " + name;
        if(type.isPrimitive()) s+= "=" + value;
        else if(type.equals(String.class)) s += "=" + value;
        else if(value == null) s += "=null";
        return s;
    }
    
    
    
}
