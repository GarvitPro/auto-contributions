// Learning Objective: This tutorial demonstrates how to visualize and interactively zoom into fractal patterns,
// specifically the Mandelbrot set, using Java's Swing graphics capabilities.
// We will learn how to:
// 1. Represent complex numbers in Java.
// 2. Implement the Mandelbrot set calculation algorithm.
// 3. Draw the fractal on a Swing window using `Graphics2D`.
// 4. Implement basic mouse-based zooming functionality.

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MandelbrotViewer extends JPanel {

    // Define the dimensions of our drawing canvas.
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    // These define the current viewable area of the complex plane.
    // Initially, we're looking at the standard Mandelbrot set region.
    private double xMin = -2.0;
    private double xMax = 1.0;
    private double yMin = -1.5;
    private double yMax = 1.5;

    // The maximum number of iterations for the Mandelbrot calculation.
    // Higher values result in more detail but take longer to compute.
    private static final int MAX_ITERATIONS = 1000;

    // This inner class will help us represent complex numbers.
    // A complex number has a real part and an imaginary part.
    private static class Complex {
        double real;
        double imaginary;

        // Constructor to initialize a complex number.
        Complex(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        // This method calculates the square of the complex number: (a + bi)^2 = (a^2 - b^2) + 2abi
        Complex square() {
            double newReal = (real * real) - (imaginary * imaginary);
            double newImaginary = 2 * real * imaginary;
            return new Complex(newReal, newImaginary);
        }

        // This method adds another complex number to this one: (a + bi) + (c + di) = (a+c) + (b+d)i
        Complex add(Complex other) {
            return new Complex(this.real + other.real, this.imaginary + other.imaginary);
        }

        // This method calculates the magnitude squared of the complex number: |a + bi|^2 = a^2 + b^2.
        // We use magnitude squared for optimization in the Mandelbrot calculation.
        double magnitudeSquared() {
            return (real * real) + (imaginary * imaginary);
        }
    }

    // The constructor sets up the initial state of our viewer.
    public MandelbrotViewer() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Set preferred size for the panel.
        // Add a mouse listener to handle zooming.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If the user clicks, we want to zoom in.
                // We'll calculate the center of the click and adjust the zoom boundaries.
                double clickX = getComplexX(e.getX());
                double clickY = getComplexY(e.getY());

                // Zoom factor: how much to zoom in. 0.5 means we zoom to half the current width/height.
                double zoomFactor = 0.5;

                // Calculate the new boundaries.
                // The new boundaries will be centered around the click point.
                double newXRange = (xMax - xMin) * zoomFactor;
                double newYRange = (yMax - yMin) * zoomFactor;

                // Update the min/max values to zoom in.
                xMin = clickX - newXRange / 2.0;
                xMax = clickX + newXRange / 2.0;
                yMin = clickY - newYRange / 2.0;
                yMax = clickY + newYRange / 2.0;

                // Request a repaint to show the zoomed-in fractal.
                repaint();
            }
        });
    }

    // This method converts a pixel x-coordinate to a complex plane x-coordinate.
    private double getComplexX(int pixelX) {
        // Linear interpolation from pixel range [0, WIDTH] to complex range [xMin, xMax].
        return xMin + (double) pixelX / WIDTH * (xMax - xMin);
    }

    // This method converts a pixel y-coordinate to a complex plane y-coordinate.
    private double getComplexY(int pixelY) {
        // Linear interpolation from pixel range [0, HEIGHT] to complex range [yMin, yMax].
        return yMin + (double) pixelY / HEIGHT * (yMax - yMin);
    }

    // This is the core Mandelbrot calculation logic.
    // For a given complex number 'c', we iterate z = z^2 + c, starting with z = 0.
    // If the magnitude of 'z' exceeds 2, it's considered to have "escaped" and will diverge to infinity.
    // The number of iterations it takes to escape determines the color.
    // If it doesn't escape within MAX_ITERATIONS, it's considered part of the Mandelbrot set.
    private int getMandelbrotIterations(Complex c) {
        Complex z = new Complex(0, 0); // Start with z = 0.
        int iterations = 0;
        // Loop until 'z' escapes (magnitude > 2) or we reach max iterations.
        while (z.magnitudeSquared() <= 4 && iterations < MAX_ITERATIONS) {
            z = z.square().add(c); // z = z^2 + c
            iterations++;
        }
        return iterations; // Return the number of iterations it took to escape.
    }

    // This method is called automatically by Swing to draw the component.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class's paintComponent.
        Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D for advanced drawing features.

        // Iterate over every pixel on our panel.
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                // Convert pixel coordinates (x, y) to a complex number 'c'.
                Complex c = new Complex(getComplexX(x), getComplexY(y));
                // Calculate how many iterations it takes for 'c' to escape.
                int iterations = getMandelbrotIterations(c);

                // Determine the color based on the number of iterations.
                // If iterations == MAX_ITERATIONS, it's likely in the Mandelbrot set (black).
                // Otherwise, use a gradient for the points outside the set.
                Color color;
                if (iterations == MAX_ITERATIONS) {
                    color = Color.BLACK; // Points inside the set are black.
                } else {
                    // Create a color based on the iteration count.
                    // This mapping can be adjusted for different visual effects.
                    float hue = (float) iterations / MAX_ITERATIONS;
                    color = Color.getHSBColor(hue, 1.0f, 1.0f);
                }
                g2d.setColor(color); // Set the drawing color.
                g2d.fillRect(x, y, 1, 1); // Draw a single pixel.
            }
        }
    }

    // The main method to run the application.
    public static void main(String[] args) {
        // Create a JFrame to hold our MandelbrotViewer.
        JFrame frame = new JFrame("Mandelbrot Set Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application closes properly.

        // Create an instance of our MandelbrotViewer.
        MandelbrotViewer viewer = new MandelbrotViewer();
        frame.add(viewer); // Add the viewer to the frame.

        frame.pack(); // Size the frame to fit the preferred size of its components.
        frame.setLocationRelativeTo(null); // Center the frame on the screen.
        frame.setVisible(true); // Make the frame visible.
    }
}