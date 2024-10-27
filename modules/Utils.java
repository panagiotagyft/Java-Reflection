package modules;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Utils {
    public List<Map.Entry<String, Integer>> get_topN(Map<String, Integer> map, int N) {
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));
        
        // Add elements to the PriorityQueue
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            minHeap.offer(entry);
            // Keep only the N largest elements in the heap
            if (minHeap.size() > N) {
                minHeap.poll();
            }
        }

        // Convert the PriorityQueue to a list and sort it in descending order
        List<Map.Entry<String, Integer>> topN = new ArrayList<>(minHeap);
        topN.sort(Comparator.<Map.Entry<String, Integer>, Integer>comparing(Map.Entry::getValue).reversed());

        // Optional: Print the top N elements
        topN.forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

        // Return the list of top N elements
        return topN;
    }

}
