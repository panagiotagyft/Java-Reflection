# Καθορισμός φακέλων και εξαρτήσεων
SRC_DIR = src
MODULES_DIR = modules
TEST_DIR = tests
BUILD_DIR = build
LIBS = libs/junit-platform-console-standalone-1.8.1.jar

.PHONY: all clean run run-program run-tests

# Στόχος all: Εκτελεί τη μεταγλώττιση του προγράμματος και των tests
all: program program_tests

# Μεταγλώττιση όλων των modules στον φάκελο build/program
program: $(patsubst $(MODULES_DIR)/%.java,$(BUILD_DIR)/program/%.class,$(shell find $(MODULES_DIR) -name "*.java"))
	@echo "Compiling Main.java..."
	javac -cp $(BUILD_DIR)/program -d . $(SRC_DIR)/Main.java

# Κανόνας για τη μεταγλώττιση κάθε αρχείου .java στον φάκελο modules σε αντίστοιχο .class στον φάκελο build/program
$(BUILD_DIR)/program/%.class: $(MODULES_DIR)/%.java
	@mkdir -p $(BUILD_DIR)/program
	@echo "Compiling $< into $@"
	javac -cp . -d $(BUILD_DIR)/program $<

# Μεταγλώττιση των tests μόνο αν τα .class αρχεία δεν είναι ενημερωμένα
program_tests: $(patsubst $(TEST_DIR)/%.java,$(BUILD_DIR)/tests/%.class,$(shell find $(TEST_DIR) -name "*.java"))

# Κανόνας για τη μεταγλώττιση κάθε αρχείου .java στον φάκελο tests σε αντίστοιχο .class στον φάκελο build/tests
$(BUILD_DIR)/tests/%.class: $(TEST_DIR)/%.java program
	@mkdir -p $(BUILD_DIR)/tests
	@echo "Compiling $< into $@"
	javac -cp $(BUILD_DIR)/program:$(LIBS) -d $(BUILD_DIR)/tests $<

# Στόχοι για εκτέλεση του προγράμματος και των tests
run: run-program run-tests

run-program: program
	@echo "Running main program with arguments: $(ARGS)"
	java -cp .:$(BUILD_DIR)/program Main $(ARGS)

run-tests: program_tests
	@echo "Running tests..."
	java -cp $(BUILD_DIR)/program:$(BUILD_DIR)/tests:$(LIBS) org.junit.platform.console.ConsoleLauncher --scan-class-path=$(BUILD_DIR)/tests

# Καθαρισμός όλων των φακέλων build και του Main.class
clean:
	@echo "Cleaning build directories and Main.class..."
	rm -rf $(BUILD_DIR)
	rm -f Main.class
