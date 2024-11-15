import java.io.IOException;
import java.io.PrintStream;

import modules.Config;
import modules.Reflection;
import modules.ManageFile;
import modules.Utils;

import java.util.Map;
import java.util.List;


public class Main {

    public static void process(Reflection reflection, List<String> classes){
                                
            // process each class
            for (String className : classes) {
                // System.out.println(className);
                    
                // ---------->          1. Fields            <----------

                // 1a. Apply reflection to find and analyze declared fields in the class.
                reflection.DeclaredFields(className);

                // 1b. Apply reflection to find and analyze all declared & inherited fields in the class.
                reflection.TotalFields(className);


                // ---------->          2. Methods           <----------

                reflection.DeclaredMethods(className);
                reflection.TotalMethods(className);


                // ---------->          4. Supertypes           <----------
                
                reflection.Supertypes(className);
            }

            // ---------->          3. Subtypes           <----------
            for (String className : classes) { reflection.Subtypes(className, classes); }
    }


    public static void extractTopN(Reflection reflection, Config config){
        
        // create utility object to sort and extract top N elements
        Utils utils = new Utils();
        
        // extract the top N 
        List<String> topN_DeclaredFields = utils.get_topN(reflection.get_DeclaredFields(), config.get_N());
        List<String> topN_TotalFields = utils.get_topN(reflection.get_TotalFields(), config.get_N());
        List<String> topN_DeclaredMethods = utils.get_topN(reflection.get_DeclaredMethods(), config.get_N());
        List<String> topN_TotalMethods = utils.get_topN(reflection.get_TotalMethods(), config.get_N());
        List<String> topN_Supertypes = utils.get_topN(reflection.get_Supertypes(), config.get_N());
        List<String> topN_Subtypes = utils.get_topN(reflection.get_Subtypes(), config.get_N());

        System.out.println("1a: " + String.join(", ", topN_DeclaredFields));
        System.out.println("1b: " + String.join(", ", topN_TotalFields));
        System.out.println("2a: " + String.join(", ", topN_DeclaredMethods));
        System.out.println("2b: " + String.join(", ", topN_TotalMethods));
        System.out.println("3:  " + String.join(", ", topN_Subtypes));
        System.out.println("4:  " + String.join(", ", topN_Supertypes));
    
    }


    public static void main(String[] args) {

       try {  
            // validate and store arguments!
            Config config = new Config(args);
            
            // redirecting output to file
            PrintStream fileOut = new PrintStream(config.get_output_file());
            System.setOut(fileOut);
            
            // create file manager and read input file
            ManageFile managefile = new ManageFile();
            managefile.read_file(config.get_input_file());

            // perform reflection process on classes
            List<String> classes = managefile.get_classes();
            Reflection reflection = new Reflection();
            process(reflection, classes);
            
            // extract the top N of the 6 requested items
            extractTopN(reflection, config);          
    
        }catch (IOException e) {
            System.out.println("An unexpected error: " + e.getMessage());
        }
    }
}
