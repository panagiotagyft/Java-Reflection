package modules;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.List;
import java.util.ArrayList;



public class ManageFile {

    private List<String> classes;
    
    public void read_file(String file_path) {

        this.classes = new ArrayList<>();

        List<String> lines = new ArrayList<>();

        Path path = Paths.get(file_path);
        try {
                
            // retrieve all lines from the input file
            lines = Files.readAllLines(path);
        
        }catch (IOException e) {
            System.out.println("An unexpected error: " + e.getMessage());
        }
        
           
        // process each class
        for (String className : lines) {       
            try {        
                Class<?> cls = Class.forName(className);
                classes.add(className);

            } catch (ClassNotFoundException e) {
                System.out.println("An unexpected error: " + e.getMessage());
            }
        }                
    }

    public List<String> get_classes(){ return classes; }
}
