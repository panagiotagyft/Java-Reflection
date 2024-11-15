package modules;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

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

    // Auxiliary Maps
    private Map<String, List<String>> pairs_supertypes;
    private Map<String, List<String>> groupedMap; 

    public Reflection(){

        this.declaredFields = new HashMap<>();
        this.totalFields = new HashMap<>();

        this.declaredMethods = new HashMap<>();
        this.totalMethods = new HashMap<>();

        this.subtypes = new HashMap<>();

        this.supertypes = new HashMap<>();

        this.pairs_supertypes = new HashMap<>();
        this.groupedMap = new HashMap<>();

    } 

    public void DeclaredFields(String className) 
    {       
        try {
            Class<?> cls = Class.forName(className);
            declaredFields.put(cls.getName(), cls.getDeclaredFields().length);
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void TotalFields(String className) 
    {
        try {
            Class<?> cls = Class.forName(className);
            Class<?> superClass = cls.getSuperclass();

            int totalFieldCount = cls.getDeclaredFields().length;

            // iterate through the class and all its superclasses
            while (superClass != null) 
            {
                for (Field field : superClass.getDeclaredFields()) 
                {    
                    int modifiers = field.getModifiers();
                    
                    // only count public and protected fields
                    if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) 
                        totalFieldCount++;
                }
                superClass = superClass.getSuperclass();
            }

            totalFields.put(className, totalFieldCount);

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void DeclaredMethods(String className) 
    {    
        try {
            Class<?> cls = Class.forName(className);
            Method[] methods = cls.getDeclaredMethods();
                   
            Set<String> auxiliary_set = new HashSet<>(); // using a HashSet to avoid duplicate entries
            
            for(Method method : methods)
                auxiliary_set.add(method.getName());
            
            declaredMethods.put(cls.getName(), auxiliary_set.size());
        } 
        catch (ClassNotFoundException e){
            System.out.println("Class " + className + " was not found!");
        }        
    }

    public void TotalMethods(String className) 
    {
        try {
            Class<?> cls = Class.forName(className);
            Set<String> auxiliary_set = new HashSet<>();

            Method[] methods = cls.getDeclaredMethods();
            for(Method method : methods)
                auxiliary_set.add(method.getName());

            Class<?> superClass = cls.getSuperclass();

            while (superClass != null) {
                Method[] superMethods = superClass.getDeclaredMethods();

                for (Method method : superMethods) 
                {
                    int modifiers = method.getModifiers();

                    // check if the method is public or protected
                    if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) 
                        auxiliary_set.add(method.getName());
                }

                superClass = superClass.getSuperclass();
            }
            totalMethods.put(className, auxiliary_set.size());

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public void Supertypes(String className) 
    {
        try {
            // Set to keep track of discovered supertypes and avoid duplicates
            Set<Class<?>> discoveredSupertypes = new HashSet<>();
            
            // Start the recursive process from the target class
            SupertypesRecursively(Class.forName(className), discoveredSupertypes);

            for (Class<?> supertype : discoveredSupertypes) 
            {
                pairs_supertypes.putIfAbsent(className, new ArrayList<>());
                pairs_supertypes.get(className).add(supertype.getName());
            }

            supertypes.put(className, discoveredSupertypes.size());

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    private void SupertypesRecursively(Class<?> currentClass, Set<Class<?>> discoveredSupertypes) 
    {
        // Retrieve the superclass of the current class
        Class<?> superClass = currentClass.getSuperclass();

        // Check if the superclass is valid, not Object, and hasn't been processed
        if (superClass != null && superClass != Object.class && discoveredSupertypes.add(superClass)) 
            SupertypesRecursively(superClass, discoveredSupertypes);

        // Process all interfaces of the current class
        Class<?>[] interfaces = currentClass.getInterfaces();
        for (Class<?> iface : interfaces) 
        {
            // If the interface hasn't been recorded, add it to discoveredSupertypes
            if (discoveredSupertypes.add(iface)) 
                SupertypesRecursively(iface, discoveredSupertypes); 
        }
    }


    public void Subtypes(String className, List<String> classes)
    {               
        Utils utils = new Utils();
            
        // If supertypes haven't been discovered yet, find them!
        // Subtypes are identified through their supertypes.
        // We examine the relationship in the opposite direction, 
        // that is, from superclass to subclass.
        if(pairs_supertypes.isEmpty())
            for (String clsName : classes){ Supertypes(clsName); }
        

        if(groupedMap.isEmpty()) 
            groupedMap = utils.groupBySuperclass(pairs_supertypes); 
       
        List<String> value = groupedMap.get(className);
        if (value != null) 
        {
            int size = value.size();
            subtypes.put(className, size);
            
        } else { subtypes.put(className, 0 ); }
        
    } 

    public Map<String, Integer> get_DeclaredFields(){ return declaredFields; }

    public Map<String, Integer> get_TotalFields(){ return totalFields; }

    public Map<String, Integer> get_DeclaredMethods(){ return declaredMethods; }

    public Map<String, Integer> get_TotalMethods(){ return totalMethods; }

    public Map<String, Integer> get_Subtypes(){ return subtypes; }

    public Map<String, Integer> get_Supertypes(){ return supertypes; }
}
