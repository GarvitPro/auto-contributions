// File: src/main/java/com/example/annotation/AutoBuilder.java

// This is our custom marker annotation.
// It will be placed on data classes for which we want to generate a builder.

package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target(ElementType.TYPE) specifies that this annotation can only be applied to types (classes, interfaces, enums).
// We want to generate builders for classes, so TYPE is appropriate.
@Target(ElementType.TYPE)
// @Retention(RetentionPolicy.SOURCE) means this annotation is only present in the source code
// and is discarded by the compiler after processing. This is crucial for annotation processing,
// as the processor runs during compilation and only needs the annotation at that stage.
// It's not needed at runtime.
@Retention(RetentionPolicy.SOURCE)
public @interface AutoBuilder {
    // No members (like 'value()') are needed for a simple marker annotation.
    // Its presence alone is enough to trigger our processor.
}


// File: src/main/java/com/example/processor/AutoBuilderProcessor.java

// Learning Objective:
// This tutorial demonstrates how to create a compile-time annotation processor
// that automatically generates a Builder class for any data object
// annotated with our custom @AutoBuilder annotation.
// You will learn:
// 1. How to define a custom annotation (`@AutoBuilder`).
// 2. The fundamental structure and lifecycle of an annotation processor.
// 3. How to read metadata (like field names and types) from annotated elements during compilation.
// 4. How to generate new Java source files programmatically using `Filer`.

package com.example.processor;

import com.example.annotation.AutoBuilder; // Import our custom annotation
import javax.annotation.processing.*;       // Core annotation processing APIs
import javax.lang.model.SourceVersion;      // For specifying Java version support
import javax.lang.model.element.*;          // For working with program elements (classes, fields, methods)
import javax.lang.model.type.TypeMirror;    // For working with types (e.g., String, int)
import javax.tools.Diagnostic;              // For reporting messages (errors, warnings, notes)
import javax.tools.JavaFileObject;          // For creating new Java source files
import java.io.IOException;                 // For file writing exceptions
import java.io.Writer;                      // For writing content to files
import java.util.Set;                       // For sets of annotations
import java.util.List;                      // For lists of fields
import java.util.StringJoiner;              // For efficiently joining strings (like constructor arguments)
import java.util.stream.Collectors;         // For collecting elements into lists

// @SupportedAnnotationTypes tells the compiler which annotations this processor is interested in.
// In our case, it's our fully qualified custom @AutoBuilder annotation.
@SupportedAnnotationTypes("com.example.annotation.AutoBuilder")
// @SupportedSourceVersion specifies the latest Java source version supported by this processor.
// We use LATEST to indicate support for the latest language features available.
@SupportedSourceVersion(SourceVersion.LATEST)
public class AutoBuilderProcessor extends AbstractProcessor {

    // Elements is a utility class that allows us to operate on program elements
    // (packages, classes, methods, fields). We use it to get information about the annotated class.
    private Elements elementUtils;
    // Filer is used to create new source, class, or auxiliary files.
    // This is how we'll write our generated Builder class's source code.
    private Filer filer;
    // Messager is used to report error messages, warnings, and other notices.
    // Useful for debugging and informing the user about processing issues.
    private Messager messager;

    // This method is called once by the annotation processing tool to initialize the processor.
    // It provides us with various utility objects (processingEnv).
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils(); // Get utility for elements
        this.filer = processingEnv.getFiler();               // Get utility for creating files
        this.messager = processingEnv.getMessager();         // Get utility for reporting messages
        // We could also get TypeUtils (processingEnv.getTypeUtils()) here if we needed to work with type comparisons.
    }

    // This is the core method where our processing logic resides.
    // It's called for each round of annotation processing.
    //
    // annotations: A set of TypeElement representing the annotation types requested by this processor
    //              (i.e., com.example.annotation.AutoBuilder).
    // roundEnv: An environment that provides access to information about the current round of annotation processing,
    //           specifically which elements are annotated with our target annotation.
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // We iterate over all elements (classes, fields, methods) that are annotated with @AutoBuilder
        // in the current compilation round.
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoBuilder.class)) {
            // We expect @AutoBuilder to be placed on classes. So, we first check the element's kind.
            if (element.getKind() == ElementKind.CLASS) {
                // If it's a class, we cast it to TypeElement to access class-specific information.
                TypeElement classElement = (TypeElement) element;
                // Now, generate the builder class for this annotated class.
                generateBuilderClass(classElement);
            } else {
                // If @AutoBuilder is applied to something other than a class (e.g., a method or field),
                // we report an error to the user via the Messager.
                messager.printMessage(
                    Diagnostic.Kind.ERROR, // This will halt compilation if errors are treated strictly
                    "@AutoBuilder can only be applied to classes.",
                    element // The element parameter links the error message to the source location.
                );
            }
        }
        // Return true to indicate that these annotations have been processed and claimed by this processor.
        // No other processor needs to process the @AutoBuilder annotation instances.
        return true;
    }

    // This method handles the actual generation of the builder class's source code.
    private void generateBuilderClass(TypeElement classElement) {
        // Get the package name of the annotated class (e.g., "com.example.example").
        String packageName = elementUtils.getPackageOf(classElement).getQualifiedName().toString();
        // Get the simple name of the annotated class (e.g., "User").
        String className = classElement.getSimpleName().toString();
        // Define the name for our generated builder class (e.g., "UserBuilder").
        String builderClassName = className + "Builder";
        // Construct the fully qualified name for the builder class (e.g., "com.example.example.UserBuilder").
        String fullBuilderClassName = packageName.isEmpty() ? builderClassName : packageName + "." + builderClassName;

        // Collect all non-static, non-final fields from the annotated class.
        // These fields will become the properties that our builder can set.
        List<? extends Element> fields = classElement.getEnclosedElements().stream()
            .filter(e -> e.getKind() == ElementKind.FIELD)                     // Only consider fields
            .filter(e -> !e.getModifiers().contains(Modifier.STATIC))         // Exclude static fields
            .filter(e -> !e.getModifiers().contains(Modifier.FINAL))          // Exclude final fields (as they can't be set externally after construction)
            .collect(Collectors.toList());

        // Use a StringBuilder to efficiently construct the source code for the new builder class.
        StringBuilder builderSource = new StringBuilder();

        // 1. Package declaration
        if (!packageName.isEmpty()) {
            builderSource.append("package ").append(packageName).append(";\n\n");
        }

        // 2. Class declaration for the builder
        builderSource.append("public class ").append(builderClassName).append(" {\n");

        // 3. Builder Fields: Declare private fields in the builder that mirror the target class's fields.
        for (Element field : fields) {
            // Get the type of the field (e.g., String, int, List<String>). TypeMirror represents this.
            TypeMirror fieldType = field.asType();
            // Get the name of the field (e.g., "name", "age").
            String fieldName = field.getSimpleName().toString();
            builderSource.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n");
        }
        builderSource.append("\n");

        // 4. Builder Constructor: A private constructor to enforce the builder pattern,
        //    meaning users must create a builder instance via a static factory method.
        builderSource.append("    private ").append(builderClassName).append("() {}\n\n");

        // 5. Static Factory Method: Provides a public entry point to get a new builder instance.
        //    (e.g., `UserBuilder.aUser()`).
        builderSource.append("    public static ").append(builderClassName).append(" a").append(className).append("() {\n");
        builderSource.append("        return new ").append(builderClassName).append("();\n");
        builderSource.append("    }\n\n");

        // 6. Setter Methods (fluent API): For each field, create a `withField()` method
        //    that sets the builder's field and returns `this` for method chaining.
        for (Element field : fields) {
            TypeMirror fieldType = field.asType();
            String fieldName = field.getSimpleName().toString();
            // Capitalize the first letter for the method name (e.g., "name" -> "Name" for `withName`).
            String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

            builderSource.append("    public ").append(builderClassName).append(" with").append(capitalizedFieldName)
                .append("(").append(fieldType).append(" ").append(fieldName).append(") {\n");
            builderSource.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            builderSource.append("        return this;\n"); // Important for fluent API chaining
            builderSource.append("    }\n\n");
        }

        // 7. Build Method: The final method to construct and return an instance of the target class.
        builderSource.append("    public ").append(className).append(" build() {\n");
        // We assume the target class has a constructor that takes all collected fields as arguments.
        // For simplicity in this beginner tutorial, we pass them in the order they were found.
        // A more robust processor might try to find a specific constructor, use setters, or require a
        // no-arg constructor.
        StringJoiner constructorArgs = new StringJoiner(", ");
        for (Element field : fields) {
            constructorArgs.add(field.getSimpleName().toString());
        }
        builderSource.append("        return new ").append(className).append("(").append(constructorArgs).append(");\n");
        builderSource.append("    }\n");

        // End of builder class declaration
        builderSource.append("}\n");

        // Write the generated source code to a new .java file.
        try {
            // Create a new Java source file using the Filer.
            JavaFileObject sourceFile = filer.createSourceFile(fullBuilderClassName);
            // Open a writer to the newly created file and write the generated source code.
            try (Writer writer = sourceFile.openWriter()) {
                writer.write(builderSource.toString());
            }
            // Report a note message indicating successful generation.
            messager.printMessage(Diagnostic.Kind.NOTE, "Generated builder: " + fullBuilderClassName);
        } catch (IOException e) {
            // If there's an error during file writing, report it as an error message.
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Failed to generate builder for " + className + ": " + e.getMessage()
            );
        }
    }
}


// File: src/main/java/com/example/example/User.java

// This is an example data class that we want to generate a builder for.
// We apply our custom @AutoBuilder annotation to it.

package com.example.example;

import com.example.annotation.AutoBuilder; // Import our custom annotation

// Apply the @AutoBuilder annotation.
// When the annotation processor runs during compilation, it will detect this
// and generate the `UserBuilder.java` file in the same package.
@AutoBuilder
public class User {
    private String name;
    private int age;
    private String email;

    // A simple constructor matching the non-static, non-final fields.
    // Our processor assumes such a constructor for simplicity to build the final object.
    // In a real-world scenario, you might have different constructors or require a no-arg constructor + setters.
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Standard getters for a data class.
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    // Optional: toString method for easy printing and debugging.
    @Override
    public String toString() {
        return "User{" +
               "name='" + name + '\'' +
               ", age=" + age +
               ", email='" + email + '\'' +
               '}';
    }
}


// File: src/main/java/com/example/example/Main.java

// This file demonstrates how you would use the *generated* UserBuilder class.
//
// IMPORTANT NOTE FOR COMPILATION:
// This `Main.java` file (and `User.java`) will need to be compiled with the
// `AutoBuilderProcessor` available on the annotation processor path.
// When compiled, `UserBuilder.java` will be generated based on `User.java`.
// Only *after* `UserBuilder.java` has been generated can this `Main.java` file
// be successfully compiled and run, as it depends on `UserBuilder`.

package com.example.example;

// We do NOT need to import UserBuilder explicitly here if it's in the same package.
// If it were in a different package, you'd import it like any other class.

public class Main {
    public static void main(String[] args) {
        // Here, we are assuming UserBuilder has been successfully generated by our processor
        // during the compilation phase.

        // The generated UserBuilder class would conceptually look something like this:
        /*
        // File: src/main/java/com/example/example/UserBuilder.java (GENERATED by AutoBuilderProcessor)
        package com.example.example;

        public class UserBuilder {
            private String name;
            private int age;
            private String email;

            private UserBuilder() {} // Private constructor

            public static UserBuilder aUser() { // Static factory method
                return new UserBuilder();
            }

            public UserBuilder withName(String name) { // Fluent setters
                this.name = name;
                return this;
            }

            public UserBuilder withAge(int age) {
                this.age = age;
                return this;
            }

            public UserBuilder withEmail(String email) {
                this.email = email;
                return this;
            }

            public User build() { // Build method to create the User object
                return new User(name, age, email); // Calls User's constructor
            }
        }
        */

        System.out.println("--- Demonstrating usage of the GENERATED UserBuilder ---");

        // Using the GENERATED UserBuilder to create a User object:
        User user1 = UserBuilder.aUser() // Start with the static factory method
                               .withName("Alice") // Chain fluent setter methods
                               .withAge(30)
                               .withEmail("alice@example.com")
                               .build(); // Finally, call build() to get the User instance

        System.out.println("Generated User 1: " + user1);
        // Expected output: Generated User 1: User{name='Alice', age=30, email='alice@example.com'}

        // Create another User, demonstrating optional fields (email in this case)
        // If a field is not set, its default value (null for objects, 0 for int) will be passed to the constructor.
        User user2 = UserBuilder.aUser()
                               .withName("Bob")
                               .withAge(25)
                               // .withEmail() is omitted, so email will be null.
                               .build();

        System.out.println("Generated User 2: " + user2);
        // Expected output: Generated User 2: User{name='Bob', age=25, email='null'}

        System.out.println("\nTutorial complete! You've learned the basics of compile-time code generation with annotation processors.");
    }
}