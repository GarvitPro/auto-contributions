// Learning Objective: This tutorial teaches how to build a simple command-line utility in Java
// that monitors a specified directory for file creation and modification events using
// the WatchService API, and calculates the SHA-256 cryptographic hash of the affected files.
// You will learn about Java's NIO.2 Path and Files APIs, WatchService for directory monitoring,
// and the MessageDigest class for cryptographic hashing.

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat; // Modern way to convert byte array to hex string (Java 17+)

public class DirectoryMonitor {

    // Main method: This is the entry point of our command-line utility.
    // It handles argument parsing, WatchService setup, and the main event loop.
    public static void main(String[] args) {
        // Step 1: Validate command-line arguments.
        // We expect exactly one argument: the path to the directory to monitor.
        if (args.length != 1) {
            System.out.println("Usage: java DirectoryMonitor <directory_path>");
            System.exit(1); // Exit if usage is incorrect, guiding the user.
        }

        // Step 2: Resolve the directory path.
        // Paths.get() creates a Path object, which is a modern way to represent file system paths
        // in Java, part of the NIO.2 API introduced in Java 7.
        Path directoryToMonitor = Paths.get(args[0]);

        // Step 3: Check if the provided path is a valid directory.
        // Files.isDirectory() is a utility method from the NIO.2 Files class
        // that checks if a Path points to an existing directory.
        if (!Files.isDirectory(directoryToMonitor)) {
            System.err.println("Error: " + directoryToMonitor.toAbsolutePath() + " is not a valid directory.");
            System.exit(1);
        }

        System.out.println("Monitoring directory: " + directoryToMonitor.toAbsolutePath());
        System.out.println("Waiting for file changes (create or modify)... Press Ctrl+C to stop.");

        // Step 4: Create a WatchService and register the directory.
        // A WatchService allows us to asynchronously monitor a directory for file system events.
        // Using try-with-resources ensures the WatchService is closed automatically
        // when the block exits, preventing resource leaks.
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            // Register the directory for specific event types.
            // StandardWatchEventKinds defines common event types.
            // ENTRY_CREATE: Notifies when a new file or directory is created.
            // ENTRY_MODIFY: Notifies when a file or directory is modified.
            // We omit ENTRY_DELETE for brevity, but it's another important type.
            directoryToMonitor.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY
            );

            // Step 5: Enter the event processing loop.
            // This loop continuously waits for WatchKeys, which represent occurred events.
            while (true) {
                // watchService.take() blocks the current thread until a WatchKey is available.
                // This means the program will pause here until a file system event happens.
                WatchKey key = watchService.take();

                // Process all events for the retrieved key. A single key might contain multiple events.
                for (WatchEvent<?> event : key.pollEvents()) {
                    // event.context() returns the Path of the affected file/directory,
                    // relative to the watched directory. It's cast to Path as we know it's a file path.
                    Path changedFileRelative = (Path) event.context();
                    // Resolve the full (absolute) path of the changed file
                    // by combining the monitored directory with the relative path.
                    Path changedFileAbsolute = directoryToMonitor.resolve(changedFileRelative);

                    // Output the type of event and the simple file name.
                    System.out.println(
                        "Event: " + event.kind() + ", File: " + changedFileAbsolute.getFileName()
                    );

                    // Check if it's a regular file (not a directory) before trying to hash it.
                    // We can only hash regular files.
                    if (Files.isRegularFile(changedFileAbsolute)) {
                        try {
                            // Calculate and display the SHA-256 hash of the changed file.
                            String hash = calculateFileHash(changedFileAbsolute);
                            System.out.println("  Full path: " + changedFileAbsolute);
                            System.out.println("  SHA-256 Hash: " + hash);
                        } catch (IOException | NoSuchAlgorithmException e) {
                            // Handle potential errors during hashing, such as:
                            // - IOException: if the file cannot be read (e.g., permissions, deleted).
                            // - NoSuchAlgorithmException: if "SHA-256" is not supported (highly unlikely).
                            System.err.println("  Error calculating hash for " + changedFileAbsolute + ": " + e.getMessage());
                        }
                    }
                }

                // Step 6: Reset the key.
                // This is crucial! If you don't reset the key, it will not register new events
                // for the directory, and the WatchService will stop working for that directory.
                boolean valid = key.reset();
                if (!valid) {
                    System.out.println("Watched directory no longer accessible. Exiting.");
                    break; // Exit the loop if the key is no longer valid (e.g., directory deleted).
                }
            }
        } catch (IOException e) {
            // Catch errors related to file system operations or WatchService creation/use.
            System.err.println("Error with WatchService: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Catch if the thread waiting for events (watchService.take()) is interrupted,
            // for example, by a user pressing Ctrl+C.
            System.out.println("Monitoring interrupted.");
            Thread.currentThread().interrupt(); // Restore the interrupted status as a best practice.
        }
    }

    // Helper method: calculateFileHash
    // This private static method takes a Path to a file and returns its SHA-256 hash
    // as a hexadecimal string. It isolates the hashing logic for clarity.
    private static String calculateFileHash(Path filePath) throws IOException, NoSuchAlgorithmException {
        // MessageDigest provides cryptographic hash functions like SHA-256.
        // We get an instance for the desired algorithm.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Files.readAllBytes(filePath) reads the entire content of the file into a byte array.
        // This is convenient for smaller to medium-sized files. For very large files,
        // streaming bytes through a FileInputStream and updating the digest incrementally
        // would be more memory-efficient to avoid loading the entire file into RAM.
        byte[] fileBytes = Files.readAllBytes(filePath);

        // Update the digest with the file's bytes and then compute the final hash.
        byte[] hashBytes = digest.digest(fileBytes);

        // HexFormat is a modern Java (since Java 17) way to convert byte arrays to hexadecimal strings.
        // It provides a cleaner and safer alternative to manual loop-based conversions using StringBuilder.
        return HexFormat.of().formatHex(hashBytes);
    }
}

// Example Usage (how to compile and run from your terminal):
//
// 1. Save the code:
//    Save this content as DirectoryMonitor.java in a file.
//
// 2. Compile:
//    Open your terminal or command prompt, navigate to the directory where you saved the file, and run:
//    javac DirectoryMonitor.java
//
// 3. Create a test directory (e.g., "my_monitored_dir"):
//    In the same terminal, create a new directory that the utility will monitor:
//    mkdir my_monitored_dir
//
// 4. Run the utility, passing the directory path as an argument:
//    java DirectoryMonitor my_monitored_dir
//
//    The program will start and print "Monitoring directory: ..."
//    It will then wait for changes.
//
// 5. Test it! While the utility is running, open *another* terminal or file explorer
//    and make changes *inside* the 'my_monitored_dir':
//
//    - Create a new file:
//      echo "Hello world from Java tutorial" > my_monitored_dir/test1.txt
//
//    - Modify an existing file:
//      echo "Adding another line to test1" >> my_monitored_dir/test1.txt
//
//    - Create another file:
//      echo "This is some secret data for testing purposes" > my_monitored_dir/secret.doc
//
//    You should see immediate output in the terminal running DirectoryMonitor,
//    showing the event type (CREATE or MODIFY), the file name, its full path,
//    and its calculated SHA-256 hash for each change.
//
// 6. Stop the utility:
//    To stop the DirectoryMonitor, go to the terminal where it's running and press Ctrl+C.