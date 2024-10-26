package modules;

import java.io.IOException;

public class Config {

    public void create_config(String[] args) throws IOException {
        
        if(args.length < 3 ){ 
            System.out.println("Missing arguments...\n");
            System.exit(1);
        }
        else if(args.length > 3 ){ 
            System.out.println("Too many arguments...\n");
            System.exit(1);
        }
        else{
            System.out.println("Success!\n");
        }
    }
}

