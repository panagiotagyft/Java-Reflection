package modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Config {

    private String input_file;
    private String output_file;
    private int value_of_N;

    public Config(String[] args) throws IOException {
        
        if(args.length < 3 ){ 
            System.out.println("Missing arguments...\n");
            System.exit(1);
        }
        else if(args.length > 3 ){ 
            System.out.println("Too many arguments...\n");
            System.exit(1);
        }

        Utils utils = new Utils();

        // Error: One or more arguments are not valid integers
        input_file = args[0];  output_file = args[1];
        if (!utils.isValidFilePath(input_file) || !utils.isValidFilePath(output_file)){ 
            System.exit(1);    
        }
        else if ( (input_file == null || input_file.isEmpty()) ) {
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
    public String get_output_file(){ return output_file; }
    public int get_N(){ return value_of_N; }

}

