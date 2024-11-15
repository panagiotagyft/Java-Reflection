# Define directories and dependencies
SRC_DIR = src
MODULES_DIR = modules
TEST_DIR = tests
BUILD_DIR = build
LIBS = libs/junit-platform-console-standalone-1.8.1.jar

.PHONY: all clean run run-program run-tests

# Target 'all': Compiles the program and the tests
all: program program_tests

# Compile all modules into the build/program directory
program: $(patsubst $(MODULES_DIR)/%.java,$(BUILD_DIR)/program/%.class,$(shell find $(MODULES_DIR) -name "*.java"))
	@echo "Compiling Main.java..."
	javac -cp $(BUILD_DIR)/program -d . $(SRC_DIR)/Main.java

# Rule for compiling each .java file in the modules directory into a corresponding .class file in the build/program directory
$(BUILD_DIR)/program/%.class: $(MODULES_DIR)/%.java
	@mkdir -p $(BUILD_DIR)/program
	@echo "Compiling $< into $@"
	javac -cp . -d $(BUILD_DIR)/program $<

# Compile tests only if the .class files are outdated
program_tests: $(patsubst $(TEST_DIR)/%.java,$(BUILD_DIR)/tests/%.class,$(shell find $(TEST_DIR) -name "*.java"))

# Rule for compiling each .java file in the tests directory into a corresponding .class file in the build/tests directory
$(BUILD_DIR)/tests/%.class: $(TEST_DIR)/%.java program
	@mkdir -p $(BUILD_DIR)/tests
	@echo "Compiling $< into $@"
	javac -cp $(BUILD_DIR)/program:$(LIBS) -d $(BUILD_DIR)/tests $<

# Targets for running the program and tests
run: run-program run-tests

run-program: program
	@echo "Running main program with arguments: $(ARGS)"
	java -cp .:$(BUILD_DIR)/program Main $(ARGS)

run-tests: program_tests
	@echo "Running tests..."
	java -cp $(BUILD_DIR)/program:$(BUILD_DIR)/tests:$(LIBS) org.junit.platform.console.ConsoleLauncher --scan-class-path=$(BUILD_DIR)/tests

# Clean up all build directories and Main.class
clean:
	@echo "Cleaning build directories and Main.class..."
	rm -rf $(BUILD_DIR)
	rm -f Main.class
