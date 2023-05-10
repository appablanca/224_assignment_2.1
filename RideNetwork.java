import java.util.*;

public class RideNetwork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of taxi pickups: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline character

        Map<String, List<String>> graph = new HashMap<>();

        System.out.println("Enter the number of taxi rides: ");
        int m = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline character

        System.out.println("Enter the taxi rides:");
        for (int i = 0; i < m; i++) {
            String[] tokens = scanner.nextLine().split(" ");
            String from = tokens[0];
            String to = tokens[1];

            // Add an entry for 'from' if it doesn't exist
            if (!graph.containsKey(from)) {
                graph.put(from, new ArrayList<>());
            }

            graph.get(from).add(to);
        }

        // rint the outgoing edges from each taxi pickup
        
        List<String> pickups = new ArrayList<>(graph.keySet());
        Collections.sort(pickups, Comparator.comparing(s -> s.substring(0, 1)));

    for (String pickup : pickups) {
        System.out.println(pickup + ": " + String.join(" ", graph.get(pickup)));
    }

        boolean isTree = isTree(graph, n);
        System.out.println((isTree ? "This ride network can be kept in a tree structure."
                                          : "This ride network cannot be kept in a tree structure."));
    }

    // Check if the given directed graph is a tree
    private static boolean isTree(Map<String, List<String>> graph, int n) {
        int numEdges = 0;

        // Check if each vertex has only one incoming edge
        Map<String, Integer> incomingEdges = new HashMap<>();
        for (String pickup : graph.keySet()) {
            incomingEdges.put(pickup, 0);
        }

        for (List<String> destinations : graph.values()) {
            numEdges += destinations.size();
            for (String dest : destinations) {
                incomingEdges.put(dest, incomingEdges.get(dest) + 1);
            }
        }

        int numRoots = 0;
        for (String pickup : graph.keySet()) {
            if (incomingEdges.get(pickup) == 0) {
                numRoots++;
            }
            if (incomingEdges.get(pickup) != 1) {
                return false;
            }
        }

        if (numRoots != 1) {
            return false;
        }

        // Check if there are no cycles
        Set<String> visited = new HashSet<>();
        if (!dfs(graph, visited, graph.keySet().iterator().next(), null)) {
            return false;
        }

        return numEdges == n - 1;
    }

    // Depth-first search to check for cycles
    private static boolean dfs(Map<String, List<String>> graph, Set<String> visited, String curr, String parent) {
        visited.add(curr);

        for (String dest : graph.get(curr)) {
            if (!visited.contains(dest)) {
                if (!dfs(graph, visited, dest, curr)) {
                    return false;
                }
            } else if (!dest.equals(parent)) {
                return false;
            }
        }

        return true;
    }
}