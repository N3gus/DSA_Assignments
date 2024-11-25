import java.util.List;
import java.util.ArrayList;

// Class to represent an item with weight, value, and ratio
class Item {
    String name;
    int weight;
    int value;
    double ratio;  // value-to-weight ratio

    public Item(String name, int weight, int value) {
        if (weight <= 0 || value < 0) {
            throw new IllegalArgumentException("Weight must be positive and value must be non-negative");
        }
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.ratio = (double) value / weight;
    }

    @Override
    public String toString() {
        return String.format("%s (Weight: %d, Value: %d, Ratio: %.2f)", 
            name, weight, value, ratio);
    }
}

public class KnapsackSolution {
    // Method to solve knapsack problem using greedy approach
    public static void solveKnapsack(Item[] items, int capacity) {
        // Add input validation
        if (items == null || items.length == 0) {
            System.out.println("Error: No items provided");
            return;
        }
        if (capacity <= 0) {
            System.out.println("Error: Invalid capacity");
            return;
        }

        // Sort items by value-to-weight ratio in descending order
        java.util.Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

        int totalWeight = 0;
        int totalValue = 0;
        List<Item> selectedItems = new ArrayList<>();

        System.out.println("\nStep-by-step selection process:");
        // Process items in sorted order
        for (Item item : items) {
            if (totalWeight + item.weight <= capacity) {
                selectedItems.add(item);
                totalWeight += item.weight;
                totalValue += item.value;
                System.out.printf("Adding %s - Current Weight: %d, Current Value: %d%n", 
                    item.name, totalWeight, totalValue);
            } else {
                System.out.printf("Skipping %s - Would exceed capacity%n", 
                    item.name);
            }
        }

        // Print results
        System.out.println("\nFinal Results:");
        System.out.println("Selected items:");
        for (Item item : selectedItems) {
            System.out.println(item);
        }
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Total Value: " + totalValue);
    }

    public static void main(String[] args) {
        // Create array of items from the problem
        Item[] items = {
            new Item("A", 5, 10),
            new Item("B", 3, 8),
            new Item("C", 2, 5),
            new Item("D", 4, 7),
            new Item("E", 1, 3),
            new Item("F", 6, 12)
        };

        int capacity = 15;  // knapsack capacity

        // Print initial items
        System.out.println("Initial items:");
        for (Item item : items) {
            System.out.println(item);
        }

        // Solve the knapsack problem
        solveKnapsack(items, capacity);
    }
}
