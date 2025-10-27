# justfile for Folley Spring Boot project

# Default recipe - show help
default:
    @just --list

# Build the project
build:
    ./gradlew build

# Run all tests
test:
    ./gradlew test

# Run tests in continuous mode (watch for changes)
test-watch:
    ./gradlew test --continuous

# Clean build artifacts
clean:
    ./gradlew clean

# Clean and rebuild
rebuild: clean build

# Run the Spring Boot application
run:
    ./gradlew bootRun

# Run the application in debug mode
run-debug:
    ./gradlew bootRun --debug-jvm

# Check for dependency updates
check-updates:
    ./gradlew dependencyUpdates

# Compile only (no tests)
compile:
    ./gradlew compileJava compileTestJava

# Run specific test class
test-class CLASS:
    ./gradlew test --tests {{CLASS}}

# Run specific test method
test-method CLASS METHOD:
    ./gradlew test --tests {{CLASS}}.{{METHOD}}

# Format code (if you add a formatter plugin later)
format:
    @echo "Code formatting not configured yet. Consider adding spotless or google-java-format"

# Generate test coverage report
coverage:
    ./gradlew test jacocoTestReport

# Package the application as a JAR
package:
    ./gradlew bootJar

# Show project dependencies
dependencies:
    ./gradlew dependencies

# Verify wrapper integrity
verify-wrapper:
    gradle wrapper --gradle-version 8.5

# Full CI pipeline (what GitHub Actions runs)
ci: clean build test

# Quick build (no tests)
quick:
    ./gradlew assemble

# Show build info
info:
    @echo "Project: Folley"
    @echo "Java Version: 17"
    @echo "Spring Boot: 3.2.0"
    @echo "Gradle: 8.5"
    ./gradlew --version
