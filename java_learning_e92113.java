// HEADER COMMENT: Learning Objective
// This tutorial demonstrates how to build a basic host application in Java
// that dynamically discovers and loads "plugin" modules from separate JAR files
// at runtime using Java's built-in ServiceLoader.
//
// You will learn:
// 1. How to define a service interface (the contract for plugins).
// 2. How to implement a plugin that adheres to this contract.
// 3. The crucial role of the 'META-INF/services' directory and its configuration file.
// 4. How the host application uses ServiceLoader to find and instantiate plugins.

package com.tutorial; // All classes are placed in a single package for simplicity in this tutorial.

import java.util.ServiceLoader; // Import the essential class for dynamic service discovery.
import java.util.logging.Logger; // A simple logging mechanism for host application output, a common practice.

// --- PART 1: The Service Interface (Contract for Plugins) ---
// This interface defines the contract that all "plugins" must implement.
// It acts as a blueprint, ensuring that any plugin provides a standardized way
// for the host application to interact with it, without knowing the plugin's
// specific implementation details beforehand.
public interface MyService { // The service interface must be public.
    /**
     * Executes the main functionality of the service.
     * This is the core method that the host application will call on discovered plugins.
     * @return A string result representing the outcome of the service's operation.
     */
    String execute();

    /**
     * Returns a name or identifier for this particular service implementation.
     * This is useful for distinguishing between different plugins that implement the same service.
     * @return The name of the service implementation (e.g., "GreetingPlugin").
     */
    String getName();
}

// --- PART 2: A Plugin Implementation (Conceptually living in a "separate JAR") ---
// This class is a concrete implementation of our 'MyService' interface.
// Conceptually, this class (and any other plugin classes) would be compiled
// into its own separate JAR file (e.g., 'greeting-plugin.jar').
// The host application won't have a direct compile-time dependency on this concrete class,
// only on the 'MyService' interface itself. This promotes loose coupling.
class GreetingPlugin implements MyService { // This class needs to implement the public MyService interface.
    // ServiceLoader requires plugin implementations to have a public, no-argument constructor.
    // It uses this constructor to create instances of the plugin when discovered.
    public GreetingPlugin() {
        // In a real application, plugins might need some initialization here,
        // but it must happen without constructor arguments if using ServiceLoader directly.
    }

    @Override
    public String execute() {
        return "Hello from GreetingPlugin!"; // This is the specific logic provided by this plugin.
    }

    @Override
    public String getName() {
        return "GreetingPlugin"; // This identifies this particular plugin implementation.
    }
}

// --- CRITICAL CONFIGURATION EXPLANATION FOR PLUGIN JAR ---
// FOR THE 'ServiceLoader' TO FIND 'GreetingPlugin' (or any other plugin),
// when the plugin is packaged into a separate JAR file (e.g., 'greeting-plugin.jar'),
// that JAR file MUST contain a special configuration file at a specific location:
//
// Location: META-INF/services/com.tutorial.MyService
// (The file name MUST be the fully qualified name of the service interface,
// in our case: 'com.tutorial.MyService').
//
// Content of the file (a single line per plugin implementation):
// com.tutorial.GreetingPlugin
// (The content MUST be the fully qualified name of the plugin implementation class,
// in our case: 'com.tutorial.GreetingPlugin').
//
// If you had another plugin, say 'com.tutorial.FarewellPlugin', the
// content of 'META-INF/services/com.tutorial.MyService' would look like this:
// com.tutorial.GreetingPlugin
// com.tutorial.FarewellPlugin
//
// This file acts as a manifest, telling the Java ServiceLoader where to find
// concrete implementations of a given service interface. It's how ServiceLoader
// discovers plugins without the host app needing to know their class names at compile time.
//
// For simplicity in this single-file tutorial, all classes are defined within
// the 'com.tutorial' package. When compiled and run from the same classpath,
// `ServiceLoader` *can* sometimes implicitly find classes that implement a service,
// but for true separate JAR deployment and robustness, the 'META-INF/services' file
// is absolutely essential and the standard mechanism!

// --- PART 3: The Host Application ---
// This is the main application that discovers and loads the plugins.
// It uses ServiceLoader to find all available implementations of 'MyService'
// that are present on its classpath at runtime.
public class PluginHostApp {

    // Using a Logger for cleaner, more structured output than System.out.println.
    private static final Logger logger = Logger.getLogger(PluginHostApp.class.getName());

    public static void main(String[] args) {
        logger.info("Starting PluginHostApp...");
        logger.info("Attempting to discover services implementing MyService interface...");

        // ServiceLoader.load(MyService.class) is the core of dynamic service discovery.
        // It takes the Class object of the service interface (MyService.class).
        // It then searches the current thread's context class loader for
        // 'META-INF/services/com.tutorial.MyService' files. For each such file found,
        // it reads the fully qualified class names listed inside and attempts to
        // instantiate them using their public, no-argument constructors.
        // It returns an Iterable<MyService>, allowing us to easily loop through all found services.
        ServiceLoader<MyService> serviceLoader = ServiceLoader.load(MyService.class);

        boolean pluginsFound = false; // Flag to track if any plugins were found.

        // Iterate through each discovered service (plugin)
        // Each 'service' object here is an instance of a concrete plugin class
        // (e.g., GreetingPlugin), but it's treated as a 'MyService' interface type.
        for (MyService service : serviceLoader) {
            pluginsFound = true; // A plugin was found!
            logger.info("  Discovered Plugin: " + service.getName());
            // Now we can use the plugin's functionality.
            // This is the power of polymorphism and ServiceLoader:
            // The host application interacts with the plugin only through the
            // 'MyService' interface, without needing to know the plugin's
            // specific concrete class at compile time.
            String result = service.execute();
            logger.info("  Executing Plugin '" + service.getName() + "': " + result);
        }

        // Provide feedback if no plugins were found, which helps in debugging.
        if (!pluginsFound) {
            logger.warning("No plugins implementing MyService were found!");
            logger.warning("Troubleshooting Tip: Ensure plugin JARs are on the classpath AND");
            logger.warning("contain the correct 'META-INF/services/com.tutorial.MyService' file.");
            logger.warning("For this single-file example, ensure GreetingPlugin is defined in the same package.");
        }

        logger.info("PluginHostApp finished.");
    }
}

// --- EXAMPLE USAGE (Conceptual Steps for Real-World Deployment with Separate JARs) ---
// To truly experience dynamic loading with separate JAR files, follow these conceptual steps:
//
// Assume you have a project structure like this:
//
// myapp/
// ├── src/
// │   └── com/
// │       └── tutorial/
// │           ├── MyService.java        (The interface)
// │           └── PluginHostApp.java    (The host application)
// └── plugin/
//     ├── src/
//     │   └── com/
//     │       └── tutorial/
//     │           └── GreetingPlugin.java (The plugin implementation)
//     └── META-INF/                  (This directory is crucial for ServiceLoader)
//         └── services/
//             └── com.tutorial.MyService  (This file *must* contain the line: "com.tutorial.GreetingPlugin")
//
// Now, the build and run steps:
//
// 1. Compile the interface and host application into a host output directory:
//    mkdir -p out/host
//    javac myapp/src/com/tutorial/MyService.java myapp/src/com/tutorial/PluginHostApp.java -d out/host
//
// 2. Compile the plugin implementation into a plugin output directory:
//    mkdir -p out/plugin
//    javac plugin/src/com/tutorial/GreetingPlugin.java -d out/plugin
//
// 3. Create the plugin's essential 'META-INF/services' directory and configuration file:
//    mkdir -p out/plugin/META-INF/services
//    echo "com.tutorial.GreetingPlugin" > out/plugin/META-INF/services/com.tutorial.MyService
//    (Make sure the content matches the fully qualified class name of the plugin!)
//
// 4. Package the plugin into its own JAR file:
//    jar -cvf greeting-plugin.jar -C out/plugin .
//    (This command packages everything in 'out/plugin' into 'greeting-plugin.jar',
//     including 'GreetingPlugin.class' and 'META-INF/services/com.tutorial.MyService'.)
//
// 5. Run the host application. Crucially, add the plugin JAR to the host's classpath:
//    java -cp out/host:greeting-plugin.jar com.tutorial.PluginHostApp
//    (On Windows, use a semicolon instead of a colon: `java -cp out/host;greeting-plugin.jar com.tutorial.PluginHostApp`)
//
//    You should see output indicating that "GreetingPlugin" was discovered and executed.
//    If you remove `greeting-plugin.jar` from the classpath, the host app will report no plugins found.
//
// --- Simplified Usage for This Single-File Tutorial (No separate JARs needed for initial learning) ---
// Since all classes (MyService, GreetingPlugin, PluginHostApp) are defined within this single file
// and in the same package ('com.tutorial'), Java's default class loading mechanism
// will make `ServiceLoader` find `GreetingPlugin` on the default classpath without needing
// an explicit `META-INF/services` file (though it's still crucial for actual separate JAR deployments).
//
// To compile and run this entire code block as a single file:
// 1. Save the entire code block above into a file named `PluginHostApp.java`.
// 2. Open a terminal or command prompt in the directory where you saved the file.
// 3. Compile the Java source file:
//    javac PluginHostApp.java
// 4. Run the compiled application:
//    java com.tutorial.PluginHostApp
//
// You should see output similar to this:
// INFO: Starting PluginHostApp...
// INFO: Attempting to discover services implementing MyService interface...
// INFO:   Discovered Plugin: GreetingPlugin
// INFO:   Executing Plugin 'GreetingPlugin': Hello from GreetingPlugin!
// INFO: PluginHostApp finished.