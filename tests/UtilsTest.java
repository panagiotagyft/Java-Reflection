import modules.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UtilsTest {

    Utils utils = new Utils();

    @Test
    public void testIsValidFilePath() {
        
        // "classes.txt" -->>> valid file path
        assertTrue(utils.isValidFilePath("classes.txt"));

        // "./invalid_path.txt" 
        assertFalse(utils.isValidFilePath("./invalid_path.txt"));
    }

    @Test
    public void testGetTopN() {
        Map<String, Integer> map = new HashMap<>();
        
        map.put("A", 27);
        map.put("B", 10);
        map.put("C", 5);
        map.put("D", 30);
        map.put("E", 25);
        map.put("G", 318);

        List<String> top4 = utils.get_topN(map, 4);

        // expecting top 4 keys with highest values: ["G", "D", "A", "E"]
        assertEquals(4, top4.size());
        assertEquals("G", top4.get(0));
        assertEquals("D", top4.get(1));
        assertEquals("A", top4.get(2));
        assertEquals("E", top4.get(3));
    }
}
