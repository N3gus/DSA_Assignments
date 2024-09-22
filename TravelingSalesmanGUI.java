import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TravelingSalesmanGUI extends JFrame {
    private JTextArea citiesTextArea;
    private JTextArea minTourTextArea;
    private JTextArea iterationTextArea;
    private JLabel minTourLengthLabel;

    public TravelingSalesmanGUI() {
        setTitle("Traveling Salesman Problem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the cities text area
        citiesTextArea = new JTextArea();
        citiesTextArea.setEditable(false);
        JScrollPane citiesScrollPane = new JScrollPane(citiesTextArea);
        add(citiesScrollPane, BorderLayout.NORTH);

        // minimum tour text area
        minTourTextArea = new JTextArea();
        minTourTextArea.setEditable(false);
        JScrollPane minTourScrollPane = new JScrollPane(minTourTextArea);
        add(minTourScrollPane, BorderLayout.CENTER);

        // tour iteration text area
        iterationTextArea = new JTextArea();
        iterationTextArea.setEditable(false);
        JScrollPane iterationScrollPane = new JScrollPane(iterationTextArea);
        add(iterationScrollPane, BorderLayout.WEST);

        // minimum tour length label
        minTourLengthLabel = new JLabel();
        add(minTourLengthLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    // display cities
    public void displayCities(List<String> cities) {
        StringBuilder citiesBuilder = new StringBuilder();
        for (String city : cities) {
            citiesBuilder.append(city).append("\n");
        }
        citiesTextArea.setText("Cities:\n" + citiesBuilder.toString());
    }

    // display minimal tour
    public void displayMinTour(List<String> minTour, int minTourLength) {
        StringBuilder minTourBuilder = new StringBuilder();
        for (String city : minTour) {
            minTourBuilder.append(city).append(" -> ");
        }
        minTourBuilder.delete(minTourBuilder.length() - 4, minTourBuilder.length());
        minTourTextArea.setText("Minimum Tour:\n" + minTourBuilder.toString());
        minTourLengthLabel.setText("Tour Distance: " + minTourLength);
    }

    // display tour iterations
    public void displayIteration(List<String> tour, int tourLength) {
        StringBuilder iterationBuilder = new StringBuilder();
        for (String city : tour) {
            iterationBuilder.append(city).append(" -> ");
        }
        iterationBuilder.delete(iterationBuilder.length() - 4, iterationBuilder.length());
        iterationTextArea.append("Tour: " + iterationBuilder.toString() + "\nLength: " + tourLength + "\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TravelingSalesmanGUI gui = new TravelingSalesmanGUI();
            gui.displayCities(TravelingSalesman.cities);

            List<String> minTour = null;
            int minTourLength = Integer.MAX_VALUE;
            List<List<String>> tours = TravelingSalesman.generatePermutations(TravelingSalesman.cities.subList(1, TravelingSalesman.cities.size()));

            for (List<String> tour : tours) {
                tour.add(0, "A");
                tour.add("A");
                int tourLength = TravelingSalesman.calculateTourLength(tour);
                gui.displayIteration(tour, tourLength);

                if (tourLength < minTourLength) {
                    minTourLength = tourLength;
                    minTour = tour;
                }
            }

            gui.displayMinTour(minTour, minTourLength);
            gui.setVisible(true);
        });
    }
}