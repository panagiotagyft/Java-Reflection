import java.io.IOException;

import modules.Config;
import modules.Reflection;
import modules.ManageFile;
import modules.Utils;

import java.util.Map;
import java.util.List;


public class Main {

    public static void main(String[] args) {
       try {           
            // validate and store arguments!
            System.out.println("Test1");
            Config config = new Config(args);
            ManageFile managefile = new ManageFile();

            managefile.read_file(config.get_input_file());

            List<String> classes = managefile.get_classes();
            
        
                    
            // create reflection and utility objects to analyze, sort, and extract top N elements
            Reflection reflection = new Reflection();
            Utils utils = new Utils();
                    
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

                reflection.Supertypes(className);
                System.out.println("!------------");
                reflection.Subtypes(className);

            }
                    
            // extract the top N declared fields
            List<String> topN_DeclaredFields = utils.get_topN(reflection.get_DeclaredFields(), config.get_N());
            System.out.println("-------------");
            List<String> topN_TotalFields = utils.get_topN(reflection.get_TotalFields(), config.get_N());
            System.out.println("-------------");
            List<String> topN_DeclaredMethods = utils.get_topN(reflection.get_DeclaredMethods(), config.get_N());
            System.out.println("-------------");
            List<String> topN_TotalMethods = utils.get_topN(reflection.get_TotalMethods(), config.get_N());
            System.out.println("-------------");
            List<String> topN_Supertypes = utils.get_topN(reflection.get_Supertypes(), config.get_N());
            System.out.println("-------------");
            List<String> topN_Subtypes = utils.get_topN(reflection.get_Subtypes(), config.get_N());
            System.out.println("-------------");
        }catch (IOException e) {
            System.out.println("An unexpected error: " + e.getMessage());
        }
    }
}
