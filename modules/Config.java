package modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Config {

    private String input_file;
    private String output_file;
    private int value_of_N;

    public static boolean isValidFilePath(String pathStr) {
        try {
            Path path = Paths.get(pathStr);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (Exception e) {
            System.out.println("This is not a valid file path.");
            return false;
        }
    }

    public Config(String[] args) throws IOException {
        
        if(args.length < 3 ){ 
            System.out.println("Missing arguments...\n");
            System.exit(1);
        }
        else if(args.length > 3 ){ 
            System.out.println("Too many arguments...\n");
            System.exit(1);
        }
        else{
            System.out.println("Success!");
        }
       
        // Error: One or more arguments are not valid integers
        input_file = args[0];  output_file = args[1];
        if (!isValidFilePath(input_file) || !isValidFilePath(output_file)){ 
            System.exit(1);    
        }
        else if ( (input_file == null || input_file.isEmpty()) 
                || (output_file == null || output_file.isEmpty()) ) {

            System.out.println("Error! Input file is not specified.");
            System.exit(1);
        }        
         
        // -- value_of_N
        try {
            value_of_N = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("This String: " + args[2] + ", is not a valid integer.");
        }
                
    }

    public String get_input_file(){ return input_file; }

    public int get_N(){ return value_of_N; }

}

