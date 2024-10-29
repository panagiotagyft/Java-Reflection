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

    public boolean isValidFilePath(String path_name) {
       
        try {
            Path path = Paths.get(path_name);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (Exception e) {
            System.out.println("This is not a valid file path.");
            return false;
        }
    
    }
   
    public List<String> get_topN(Map<String, Integer> map, int N) {
               
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
            (val1, val2) -> val1.getValue().compareTo(val2.getValue())
        );

        // save only the N largest elements in the min heap
        for (Map.Entry<String, Integer> element : map.entrySet()) {
            minHeap.offer(element);
            
            // keep only the N largest elements!
            if (minHeap.size() > N) {
                minHeap.poll();
            }
        }

        minHeap.forEach(System.out::println);

        // extract the keys from the minheap and insert them in reverse order into the list
        List<String> topN = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            topN.add(0, minHeap.poll().getKey());
        }

        // Return the list of top N keys
        return topN;
    }

    public Map<String, List<String>> reverseAndGroup(Map<String, String> pairs) {
        Map<String, List<String>> groupedMap = new HashMap<>();

        for (Map.Entry<String, String> entry : pairs.entrySet()) {
            String point1 = entry.getKey();
            String point2 = entry.getValue();

            // Δημιουργούμε την αντιστροφή (point2, point1) και την προσθέτουμε στον νέο χάρτη
            groupedMap.putIfAbsent(point2, new ArrayList<>());
            groupedMap.get(point2).add(point1);
        }

        return groupedMap;
    }


}
