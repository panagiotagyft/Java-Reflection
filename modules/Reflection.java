package modules;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Reflection {
    
    // Fields
    private Map<String, Integer> declaredFields;
    private Map<String, Integer> totalFields;

    // Methods
    private Map<String, Integer> declaredMethods;
    private Map<String, Integer> totalMethods;

    // Subtypes
    private Map<String, Integer> subtypes;

    // Supertypes
    private Map<String, Integer> supertypes;

    private Map<String, String> pairs_supertypes;

    public Reflection(){

        this.declaredFields = new HashMap<>();
        this.totalFields = new HashMap<>();

        this.declaredMethods = new HashMap<>();
        this.totalMethods = new HashMap<>();

        this.subtypes = new HashMap<>();

        this.supertypes = new HashMap<>();

        this.pairs_supertypes = new HashMap<>();

    } 

    public void DeclaredFields(String className) {
        
        try {
            Class<?> cls = Class.forName(className);
            declaredFields.put(cls.getName(), cls.getDeclaredFields().length);
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void TotalFields(String className) {

        try {
            Class<?> cls = Class.forName(className);
            Class<?> superClass = cls.getSuperclass();

            int totalFieldCount = cls.getDeclaredFields().length;

            // iterate through the class and all its superclasses
            while (superClass != null) {

                for (Field field : superClass.getDeclaredFields()) {
                    
                    int modifiers = field.getModifiers();
                    
                    // only count public and protected fields
                    if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                        totalFieldCount++;
                    }
                }
                superClass = superClass.getSuperclass();
            }

            totalFields.put(className, totalFieldCount);

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void DeclaredMethods(String className) {
        
        try {
            Class<?> cls = Class.forName(className);
            Method[] methods = cls.getDeclaredMethods();
           
            // System.out.println(methods.length);
           
            Set<String> auxiliary_set = new HashSet<>();
            
            for(Method method : methods)
                auxiliary_set.add(method.getName());
            
            declaredMethods.put(cls.getName(), auxiliary_set.size());

        } 
        catch (ClassNotFoundException e){
            System.out.println("Class " + className + " was not found!");
        }        
    }

    public void TotalMethods(String className) {

        try {
            Class<?> cls = Class.forName(className);
            Set<String> auxiliary_set = new HashSet<>();

            while (cls != null) {
                Method[] methods = cls.getDeclaredMethods();

                for (Method method : methods) {
                    int modifiers = method.getModifiers();

                    // check if the method is not static
                    if (!Modifier.isStatic(modifiers)) {
                        // check if the method is public or protected
                        if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                            auxiliary_set.add(method.getName());
                        }
                    }
                }

                cls = cls.getSuperclass();
            }

            totalMethods.put(className, auxiliary_set.size());
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void Supertypes(String className) {
        
        Stack<Class<?>> auxiliary_stack = new Stack<>();

        try {

            Set<Class<?>> supertypes1 = new HashSet<>();

            // begin with the target class.
            stack.push(Class.forName(className));

            while (!stack.isEmpty()) {
                Class<?> current_cls = stack.pop();

                // Προσθέτουμε την υπερκλάση αν δεν είναι ήδη στη λίστα
                Class<?> superClass = current_cls.getSuperclass();
                if (superClass != null && superClass != Object.class && supertypes1.add(superClass)) {
                    pairs_supertypes.put(current_cls.getName(), superClass.getName());
                    stack.push(superClass); // Προσθήκη της υπερκλάσης στη στοίβα
                }

                // Προσθήκη όλων των interfaces
                for (Class<?> iface : current_cls.getInterfaces()) {
                    if (supertypes1.add(iface)) {
                        pairs_supertypes.put(current_cls.getName(), iface.getName());
                        stack.push(iface); // Προσθήκη του interface στη στοίβα
                    }
                }
            }
            
            // Εκτύπωση όλων των υπερτύπων
            supertypes1.forEach(supertype -> System.out.println(supertype.getName()));

            supertypes.put(className, supertypes1.size());
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }

    }

    public void Subtypes(String className){
        Utils utils = new Utils();

        Map<String, List<String>> groupedMap = utils.reverseAndGroup(pairs_supertypes);
        
        subtypes.put(className, groupedMap.size());

    }


    public Map<String, Integer> get_DeclaredFields(){ return declaredFields; }
    
    public Map<String, Integer> get_TotalFields(){ return totalFields; }

    public Map<String, Integer> get_DeclaredMethods(){ return declaredMethods; }

    public Map<String, Integer> get_TotalMethods(){ return totalMethods; }

    public Map<String, Integer> get_Subtypes(){ return subtypes; }

    public Map<String, Integer> get_Supertypes(){ return supertypes; }
}
