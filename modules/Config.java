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

    public void create_config(String[] args) throws IOException {
        
        if(args.length < 3 ){ 
            System.out.println("Missing arguments...\n");
            System.exit(1);
        }
        else if(args.length > 3 ){ 
            System.out.println("Too many arguments...\n");
            System.exit(1);
        }
        // else{
        //     System.out.println("Success!\n");
        // }

        // Error: One or more arguments are not valid integers
        input_file = args[0];  output_file = args[1];
        if (!isValidFilePath(input_file) || !isValidFilePath(output_file)) 
            System.exit(1);
                   
        
        // -- value_of_N
        try {
            value_of_N = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("This String: " + args[2] + ", is not a valid integer.");
        }
                
    }

}

