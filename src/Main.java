import java.io.IOException;
import modules.Config;
import modules.Reflection;
import modules.Utils;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        try {
            // check arguments and save them!
            System.out.println("Test1");
            Config config = new Config(args);
        
            Path path = Paths.get(config.get_input_file());
            try {
                    
                List<String> lines = Files.readAllLines(path);
        
                Reflection reflection = new Reflection();
                Utils utils = new Utils();
                
        
                for (String line : lines) {
                    System.out.println(line);
                    
                    reflection.DeclaredFields(line);
                }
                List<Map.Entry<String, Integer>> topN_DeclaredFields = utils.get_topN(reflection.get_DeclaredFields(), config.get_N());

            } catch (IOException e) {
                System.out.println("Error! Cannot read file!");
                System.exit(1);
            }

        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("An unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}
