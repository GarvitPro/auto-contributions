// LEARNING OBJECTIVE:
// This tutorial teaches you how to build a miniature unit testing framework using Java Reflection and Custom Annotations.
// You will learn how to:
// 1. Define a custom marker annotation.
// 2. Use Java Reflection to discover methods annotated with your custom annotation.
// 3. Dynamically invoke these methods at runtime.
// This forms the core mechanism of how frameworks like JUnit discover and run your test methods!

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// STEP 1: Define a Custom Annotation
// This is our custom annotation named '@MyTest'.
// Its purpose is to mark methods that should be treated as test methods by our framework.
// @Retention(RetentionPolicy.RUNTIME): This is crucial! It tells Java to keep this annotation
//                                     available at runtime, so we can detect it with Reflection.
// @Target(ElementType.METHOD): This specifies that '@MyTest' can only be applied to methods.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyTest {
    // A marker annotation usually doesn't need any elements (like 'value()').
    // Its presence alone is enough to convey meaning.
}

// STEP 2: Create our Miniature Test Runner
// This class will be responsible for finding and executing test methods.
class MyTestRunner {

    private int testsRun = 0;
    private int testsPassed = 0;
    private int testsFailed = 0;

    // The main method of our runner: takes a Class object representing a test class.
    public void runTests(Class<?> testClass) {
        System.out.println("-------------------------------------");
        System.out.println("Running tests for: " + testClass.getName());
        System.out.println("-------------------------------------");

        try {
            // We need an instance of the test class to invoke its methods.
            // testClass.getDeclaredConstructor().newInstance() creates a new object
            // of the test class using its default (no-argument) constructor.
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            // Get all methods declared in the test class.
            // .getMethods() would include inherited methods, .getDeclaredMethods() only
            // includes methods directly declared by this class, which is usually what
            // we want for test method discovery.
            Method[] methods = testClass.getDeclaredMethods();

            // Iterate through each method to find our test methods.
            for (Method method : methods) {
                // Check if the current method has our custom '@MyTest' annotation.
                // .isAnnotationPresent() is a key Reflection API method for this.
                if (method.isAnnotationPresent(MyTest.class)) {
                    testsRun++;
                    System.out.print("  Running test: " + method.getName() + " -> ");
                    try {
                        // Invoke the test method!
                        // method.invoke(testInstance): Calls the method on the specific
                        // 'testInstance' object. If the method were static, 'null' could be passed.
                        method.invoke(testInstance);
                        testsPassed++;
                        System.out.println("PASSED");
                    } catch (InvocationTargetException e) {
                        // This exception wraps any exception thrown by the invoked method.
                        // For unit tests, typically an AssertionError (or similar) is thrown
                        // when an assertion fails. We catch this to mark the test as failed.
                        Throwable actualException = e.getTargetException();
                        if (actualException instanceof AssertionError) {
                            testsFailed++;
                            System.err.println("FAILED: " + actualException.getMessage());
                        } else {
                            // If it's another type of exception, it's an unexpected error in the test itself.
                            testsFailed++;
                            System.err.println("ERROR: " + actualException.getClass().getSimpleName() + " - " + actualException.getMessage());
                        }
                    } catch (IllegalAccessException | IllegalArgumentException e) {
                        // These exceptions indicate a problem with method access or arguments.
                        // (e.g., trying to invoke a private method without setting accessible(true),
                        // or passing wrong arguments if the method had parameters).
                        // For '@MyTest' methods, we assume they are public and take no arguments.
                        testsFailed++;
                        System.err.println("ERROR during invocation: " + e.getMessage());
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // These exceptions can occur if the test class itself cannot be instantiated
            // (e.g., no default constructor, constructor is private) or if there's
            // a problem getting the constructor.
            System.err.println("FATAL ERROR: Could not run tests for " + testClass.getName());
            System.err.println("  Reason: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace for debugging framework issues
        } finally {
            // Always print the summary, regardless of success or failure.
            printSummary();
        }
    }

    private void printSummary() {
        System.out.println("-------------------------------------");
        System.out.println("Test Results:");
        System.out.println("  Total Tests Run: " + testsRun);
        System.out.println("  Tests Passed:    " + testsPassed);
        System.out.println("  Tests Failed:    " + testsFailed);
        System.out.println("-------------------------------------");
    }
}

// STEP 3: Create an Example Test Class
// This class demonstrates how users would write tests using our new framework.
class MyExampleTests {

    // This method is marked with '@MyTest', so our runner will discover and execute it.
    @MyTest
    public void testAddition() {
        // Simple assertion: If the condition is false, an AssertionError is thrown.
        // This is how test failures are typically indicated in unit testing.
        if (2 + 2 != 4) {
            throw new AssertionError("Addition test failed: 2 + 2 should be 4");
        }
        // If no exception is thrown, the test passes.
    }

    @MyTest
    public void testSubtraction() {
        if (5 - 3 != 2) {
            throw new AssertionError("Subtraction test failed: 5 - 3 should be 2");
        }
    }

    // This test is intentionally designed to fail to show failure reporting.
    @MyTest
    public void testIntendedFailure() {
        // We simulate a failing assertion here.
        if (10 / 2 != 4) { // 10 / 2 is 5, not 4
            throw new AssertionError("Intended failure test: 10/2 is not 4");
        }
    }

    // This method is NOT marked with '@MyTest'. Our runner will ignore it.
    public void helperMethod() {
        System.out.println("This is a helper method, it should not be run as a test.");
    }
}

// STEP 4: Main Application to Run the Tests
// This is where we orchestrate the running of our test suite.
public class Main {
    public static void main(String[] args) {
        // Create an instance of our test runner.
        MyTestRunner runner = new MyTestRunner();

        // Pass our example test class to the runner.
        // MyExampleTests.class provides the Class object needed by the runner.
        runner.runTests(MyExampleTests.class);

        // You could add more test classes here:
        // runner.runTests(AnotherExampleTests.class);
    }
}