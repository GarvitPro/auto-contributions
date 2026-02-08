// Learning Objective: This tutorial teaches how to fetch data from a public API in Java
// and visualize it using a simple charting library. We'll focus on making HTTP requests,
// parsing JSON responses, and integrating with a charting tool.

// We'll use the Jsoup library for making HTTP requests and parsing HTML (which can also parse JSON effectively).
// For charting, we'll use JFreeChart, a widely used and robust charting library.

// --- Dependency Setup (Not part of the code itself, but crucial for running) ---
// To run this code, you'll need to add the following dependencies to your project.
// If you're using Maven, add these to your pom.xml:
//
// <dependency>
//     <groupId>org.jsoup</groupId>
//     <artifactId>jsoup</artifactId>
//     <version>1.17.2</version>
// </dependency>
// <dependency>
//     <groupId>org.jfree</groupId>
//     <artifactId>jfreechart</artifactId>
//     <version>1.5.4</version>
// </dependency>
//
// If you're using Gradle, add these to your build.gradle:
//
// implementation 'org.jsoup:jsoup:1.17.2'
// implementation 'org.jfree:jfreechart:1.5.4'
// -------------------------------------------------------------------------------

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiDataVisualizer {

    // This is the main class that will orchestrate fetching and displaying data.

    public static void main(String[] args) {
        // The entry point of our application.
        // We'll use SwingUtilities.invokeLater to ensure GUI operations are done on the Event Dispatch Thread (EDT).
        // This is a best practice for Swing applications.
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Define the API URL.
                // For this example, we'll use a simple public API that returns stock data as JSON.
                // This API is a placeholder; you'd replace it with a real API you're interested in.
                // A common example might be a weather API, cryptocurrency API, etc.
                // The following URL is for demonstration purposes and might not work or change.
                String apiUrl = "https://api.example.com/stockdata/AAPL"; // Replace with a real API URL

                // 2. Fetch data from the API.
                // The fetchDataFromApi method handles the HTTP request and parsing.
                List<StockData> stockDataList = fetchDataFromApi(apiUrl);

                // 3. Create and display the chart.
                // The createAndShowChart method takes the fetched data and creates a visual representation.
                createAndShowChart(stockDataList);

            } catch (IOException e) {
                // Handle potential network or parsing errors.
                System.err.println("Error fetching or processing API data: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // This method fetches data from a given API URL.
    private static List<StockData> fetchDataFromApi(String url) throws IOException {
        List<StockData> data = new ArrayList<>();
        System.out.println("Fetching data from: " + url);

        // Jsoup.connect(url) establishes a connection to the URL.
        // .get() executes the GET request and returns a Document object representing the parsed HTML/JSON.
        Document doc = Jsoup.connect(url).ignoreContentType(true).get(); // ignoreContentType(true) is useful for JSON

        // In a real scenario, you'd parse JSON here. Jsoup's select can sometimes work for simple JSON structures too,
        // but for robust JSON parsing, libraries like Jackson or Gson are recommended.
        // For this example, we'll simulate parsing by assuming a structure.
        // If the API returned JSON like: {"date": "2023-10-26", "price": 170.50}
        // we would use a JSON parser.

        // Let's simulate fetching a few data points for demonstration.
        // In a real application, you'd extract data from the `doc` based on the API's response format.
        // For example, if it was JSON:
        // JSONObject jsonObject = new JSONObject(doc.text());
        // JSONArray items = jsonObject.getJSONArray("stockItems");
        // for (int i = 0; i < items.length(); i++) {
        //     JSONObject item = items.getJSONObject(i);
        //     String date = item.getString("date");
        //     double price = item.getDouble("price");
        //     data.add(new StockData(date, price));
        // }

        // --- SIMULATED DATA FETCHING FOR EDUCATIONAL PURPOSES ---
        // Since we don't have a guaranteed working public JSON API for simple stock data here,
        // we'll create some sample data. Replace this block with actual JSON parsing if you have a real API.
        System.out.println("Simulating data fetching...");
        data.add(new StockData("2023-10-01", 170.50));
        data.add(new StockData("2023-10-02", 171.20));
        data.add(new StockData("2023-10-03", 170.80));
        data.add(new StockData("2023-10-04", 172.00));
        data.add(new StockData("2023-10-05", 173.50));
        // --- END OF SIMULATED DATA ---

        return data;
    }

    // This method creates a chart from the fetched data and displays it in a JFrame.
    private static void createAndShowChart(List<StockData> stockDataList) {
        // 1. Create a dataset for the chart.
        // DefaultCategoryDataset is suitable for simple line or bar charts where we have categories (like dates).
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate the dataset with our fetched stock data.
        for (StockData stockData : stockDataList) {
            // For a line chart, we typically have a row key (e.g., "Price"),
            // a column key (e.g., the date), and the value (the price).
            dataset.addValue(stockData.getPrice(), "Stock Price", stockData.getDate());
        }

        // 2. Create the chart.
        // ChartFactory.createLineChart is a convenient way to generate a line chart.
        // Arguments:
        // - Chart title: "Stock Price Over Time"
        // - Category axis label: "Date"
        // - Value axis label: "Price ($)"
        // - Dataset: The data we prepared.
        // - Plot orientation: PlotOrientation.VERTICAL for a standard vertical chart.
        // - Legend: true to show the legend (e.g., "Stock Price").
        // - Tooltips: true to show tooltips when hovering over data points.
        // - URLs: false (not needed for this simple example).
        JFreeChart chart = ChartFactory.createLineChart(
                "Stock Price Over Time",
                "Date",
                "Price ($)",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // 3. Create a panel to hold the chart.
        // ChartPanel is a Swing component that displays the JFreeChart.
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600)); // Set a preferred size for the panel.

        // 4. Create a JFrame to display the chart panel.
        JFrame frame = new JFrame("Stock Data Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation: exit the application when the window is closed.
        frame.setContentPane(chartPanel); // Add the chart panel to the frame's content pane.
        frame.pack(); // Pack the frame to size it based on the preferred sizes of its components.
        frame.setLocationRelativeTo(null); // Center the frame on the screen.
        frame.setVisible(true); // Make the frame visible.
    }

    // A simple inner class to hold our stock data.
    // This makes our data more organized and readable.
    private static class StockData {
        private final String date;
        private final double price;

        public StockData(String date, double price) {
            this.date = date;
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public double getPrice() {
            return price;
        }
    }

    // --- Example Usage ---
    // To run this code:
    // 1. Make sure you have Jsoup and JFreeChart libraries added to your project.
    // 2. Compile and run the `ApiDataVisualizer` class.
    //
    // This will:
    // 1. Attempt to fetch data from the (simulated) API URL.
    // 2. If successful, it will create and display a line chart in a new window showing
    //    "Stock Price Over Time" based on the fetched (or simulated) data.
    //
    // IMPORTANT: Replace "https://api.example.com/stockdata/AAPL" with a real public API URL
    // that returns data you can parse. You will also need to adjust the `fetchDataFromApi`
    // method to correctly parse the JSON or XML response from your chosen API.
    // For robust JSON parsing, consider using libraries like Jackson or Gson.
}