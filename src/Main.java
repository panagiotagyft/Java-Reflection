import java.io.IOException;
import modules.Config;

public class Main {
    
    public static void main(String[] args) {
        try {
            Config config = new Config();
            config.create_config(args);
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error: " + e.getMessage());
        }
    }
}
