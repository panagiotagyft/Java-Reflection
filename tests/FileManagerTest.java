package modules;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManagerTest {

    @Test
    public void testReadFile() throws IOException 
    {    
        Path file = Files.createTempFile("auxiliaryFile", ".txt");
        Files.write(file, List.of("java.lang.String", "java.util.ArrayList"));
        
        FileManager fileManager = new FileManager();

        fileManager.read_file(file.toString());
        List<String> classes = fileManager.get_classes();

        assertEquals(2, classes.size());
        assertTrue(classes.contains("java.lang.String"));
        assertTrue(classes.contains("java.util.ArrayList"));

        Files.deleteIfExists(file);
    }
}
