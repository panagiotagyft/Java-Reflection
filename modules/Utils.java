package modules;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Utils {

    public boolean isValidFilePath(String path_name) 
    {
        try {
            Path path = Paths.get(path_name);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (Exception e) {
            System.out.println("This is not a valid file path.");
            return false;
        }
    
    }
   
    public List<String> get_topN(Map<String, Integer> map, int N) 
    {   
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
            (val1, val2) -> val1.getValue().compareTo(val2.getValue())
        );

        // save only the N largest elements in the min heap
        for (Map.Entry<String, Integer> element : map.entrySet()) 
        {
            minHeap.offer(element);
            
            // keep only the N largest elements!
            if (minHeap.size() > N) {
                minHeap.poll();
            }
        }

        // extract the keys from the minheap and insert them in reverse order into the list
        List<String> topN = new ArrayList<>();
        while (!minHeap.isEmpty()) 
        {
            Map.Entry<String, Integer> entry = minHeap.poll();
            topN.add(0, entry.getKey() + "=" + entry.getValue());
        }
 
        // Return the list of top N keys
        return topN;
    }


    public Map<String, List<String>> groupBySuperTypes(Map<String, List<String>> pairs) 
    {
        //initialize a new map to store the supertype as key and its subtypes as values
        Map<String, List<String>> groupedMap = new HashMap<>();

        // iterate over each (classname, [supertypes]) entry in the input map
        for (Map.Entry<String, List<String>> entry : pairs.entrySet()) 
        {
            String subclass = entry.getKey();
            List<String> supertypes = entry.getValue();

            // iterate over each supertype in the list of supertypes
            for (String supertype : supertypes) 
            {
                // group by supertype, adding the subclass to its list
                groupedMap.putIfAbsent(supertype, new ArrayList<>());
                groupedMap.get(supertype).add(subclass);
            }
        }

        // Return the map with (superttypes]) structure
        return groupedMap;
    }



}
