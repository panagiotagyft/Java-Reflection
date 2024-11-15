import modules.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


public class UtilsTest {

    Utils utils = new Utils();

    @Test
    public void testIsValidFilePath() 
    {    
        // "classes.txt" -->>> valid file path
        assertTrue(utils.isValidFilePath("classes.txt"));

        // "./invalid_path.txt" 
        assertFalse(utils.isValidFilePath("./invalid_path.txt"));
    }

    @Test
    public void testGetTopN() 
    {
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
        assertEquals("G=318", top4.get(0));
        assertEquals("D=30", top4.get(1));
        assertEquals("A=27", top4.get(2));
        assertEquals("E=25", top4.get(3));
    }

    @Test
    public void testgroupBySuperTypes() 
    {
        Map<String, List<String>> pairs_subtypes =  new HashMap<>();
        List<String> auxiliary_list = new ArrayList<>();
        
        auxiliary_list.add("1"); auxiliary_list.add("2"); auxiliary_list.add("3");
    
        for (String i : auxiliary_list) {
            pairs_subtypes.putIfAbsent(i, new ArrayList<>());

            if (i.equals("1") || i.equals("3")) {
                pairs_subtypes.get(i).add("2");
            }
            pairs_subtypes.get(i).add("4");
        }

        // assuming utils.groupBySuperTypes works correctly
        Map<String, List<String>> groupbysupertypes = utils.groupBySuperTypes(pairs_subtypes);
        
        Map<String, List<String>> expectedResults = new HashMap<>();
        expectedResults.put("2", Arrays.asList("1", "3"));
        expectedResults.put("4", Arrays.asList("1", "2", "3"));

        // validation
        for (Map.Entry<String, List<String>> entry : groupbysupertypes.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();

            if (expectedResults.containsKey(key)) {
                assertEquals(expectedResults.get(key), value, "Unexpected values for key: " + key);
            } else {
                fail("The specific key is not expected: " + key);
            }
        }

    }
}
