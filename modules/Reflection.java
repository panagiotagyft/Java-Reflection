package modules;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Reflection {
    
    private Map<String, Integer> declaredFields;
    private Map<String, Integer> totalFields;

    public Reflection(){
        this.declaredFields = new HashMap<>();
        this.totalFields = new HashMap<>();
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

    public void TotalFields(String className){

        try {
            Class<?> cls = Class.forName(className);
            Class<?> superClass = cls.getSuperclass();

            int totalFieldCount = cls.getDeclaredFields().length;
            while (superClass != null) {
                totalFieldCount += superClass.getDeclaredFields().length;
                superClass = superClass.getSuperclass();
            }
            totalFields.put(className, totalFieldCount);

        } catch (ClassNotFoundException e) {
            System.out.println("Class " + className + " was not found!");
        }
    }

    public Map<String, Integer> get_DeclaredFields(){ return declaredFields; }
    public Map<String, Integer> get_TotalFields(){ return totalFields; }
}
