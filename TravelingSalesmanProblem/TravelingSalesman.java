import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelingSalesman {
    // Define the cities
    static List<String> cities = Arrays.asList("A", "B", "C", "D", "E", "F");

    // Define the distances between cities
    private static Map<String, Map<String, Integer>> distances = new HashMap<>();

    static {
        // Initialize the distances
        Map<String, Integer> distancesFromA = new HashMap<>();
        distancesFromA.put("A", 0);
        distancesFromA.put("B", 10);
        distancesFromA.put("C", 15);
        distancesFromA.put("D", 20);
        distancesFromA.put("E", 25);
        distancesFromA.put("F", 30);
        distances.put("A", distancesFromA);

        Map<String, Integer> distancesFromB = new HashMap<>();
        distancesFromB.put("A", 10);
        distancesFromB.put("B", 0);
        distancesFromB.put("C", 12);
        distancesFromB.put("D", 18);
        distancesFromB.put("E", 22);
        distancesFromB.put("F", 26);
        distances.put("B", distancesFromB);

        Map<String, Integer> distancesFromC = new HashMap<>();
        distancesFromC.put("A", 15);
        distancesFromC.put("B", 12);
        distancesFromC.put("C", 0);
        distancesFromC.put("D", 10);
        distancesFromC.put("E", 14);
        distancesFromC.put("F", 18);
        distances.put("C", distancesFromC);

        Map<String, Integer> distancesFromD = new HashMap<>();
        distancesFromD.put("A", 20);
        distancesFromD.put("B", 18);
        distancesFromD.put("C", 10);
        distancesFromD.put("D", 0);
        distancesFromD.put("E", 12);
        distancesFromD.put("F", 16);
        distances.put("D", distancesFromD);

        Map<String, Integer> distancesFromE = new HashMap<>();
        distancesFromE.put("A", 25);
        distancesFromE.put("B", 22);
        distancesFromE.put("C", 14);
        distancesFromE.put("D", 12);
        distancesFromE.put("E", 0);
        distancesFromE.put("F", 10);
        distances.put("E", distancesFromE);

        Map<String, Integer> distancesFromF = new HashMap<>();
        distancesFromF.put("A", 30);
        distancesFromF.put("B", 26);
        distancesFromF.put("C", 18);
        distancesFromF.put("D", 16);
        distancesFromF.put("E", 10);
        distancesFromF.put("F", 0);
        distances.put("F", distancesFromF);
    }

    public static void main(String[] args) {
        // Generate all permutations of cities, excluding the starting city
        List<List<String>> tours = generatePermutations(cities.subList(1, cities.size()));

        // Initialize the minimum tour length to a large number
        int minTourLength = Integer.MAX_VALUE;
        List<String> minTour = null;

        // Iterate over all possible tours
        for (List<String> tour : tours) {
            // Start the tour at the starting city
            tour.add(0, "A");
            tour.add("A");

            // Calculate the length of the tour
            int tourLength = calculateTourLength(tour);

            // If the tour is shorter than the current minimum, update the minimum
            if (tourLength < minTourLength) {
                minTourLength = tourLength;
                minTour = new ArrayList<>(tour);
            }
        }

        System.out.println("Minimum Tour: " + minTour);
        System.out.println("Tour Distance: " + minTourLength);
    }

    // Generate all permutations of a list
    static List<List<String>> generatePermutations(List<String> list) {
        List<List<String>> permutations = new ArrayList<>();
        generatePermutations(list, 0, permutations);
        return permutations;
    }

    // Helper method to generate permutations recursively
    private static void generatePermutations(List<String> list, int start, List<List<String>> permutations) {
        if (start == list.size() - 1) {
            permutations.add(new ArrayList<>(list));
        } else {
            for (int i = start; i < list.size(); i++) {
                swap(list, start, i);
                generatePermutations(list, start + 1, permutations);
                swap(list, start, i);
            }
        }
    }

    // Swap elements in a list
    private static void swap(List<String> list, int i, int j) {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Calculate the length of a tour
    static int calculateTourLength(List<String> tour) {
        int length = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            length += distances.get(tour.get(i)).get(tour.get(i + 1));
        }
        return length;
    }
}
