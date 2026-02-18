package com.aem.geeks.core.javarelatedcodes.junitrelated;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JunitExampleClassTest {

    private JunitExampleClass junitExampleClass;

    //JUnit creates new test object per test method - So according to that only a static increment variable will work. because static belongs to class not obj.
    /*if you create the below variable without static then it won't increase, because for every test run junit creates a new obj and for every new obj the count var
        set to "0" */
    public static int count = 0;

    // Runs once before all test methods (must be static)
    @BeforeAll
    static void beforeAllTests() {
        System.out.println("Ran Before all tests executed");
    }

    // Runs before each test method
    @BeforeEach
    void setUp() {
        count = count+1;
        junitExampleClass = new JunitExampleClass(); // fresh object before every test
        System.out.println("running Before each test - " + count);
    }

    // Runs after each test method
    @AfterEach
    void tearDown() {
        System.out.println("Running After each test");
    }

    // Runs once after all test methods
    @AfterAll
    static void afterAllTests() {
        System.out.println("After all tests executed");
    }

    // ---------------- Assertions ----------------

    @Test
    void testAssertEquals() {
        // checks expected value equals actual value
        assertEquals(5, junitExampleClass.add(2, 3));
    }

    @Test
    void testAssertNotEquals() {
        // verifies values are NOT equal
        assertNotEquals(6, junitExampleClass.add(2, 3));
    }

    @Test
    void testAssertTrue() {
        // checks condition is true
        assertTrue(junitExampleClass.add(2, 3) == 5);
    }

    @Test
    void testAssertFalse() {
        // checks condition is false
        assertFalse(junitExampleClass.add(2, 3) == 10);
    }

    @Test
    void testAssertNull() {
        // verifies value is null
        assertNull(junitExampleClass.getNullValue());
    }

    @Test
    void testAssertNotNull() {
        // verifies value is not null
        assertNotNull(junitExampleClass.getMessage());
    }

    @Test
    void testAssertSame() {
        // verifies both references point to SAME object
        String str = junitExampleClass.getMessage();
        assertSame("Hello", str);
    }

    @Test
    void testAssertNotSame() {
        // verifies references are NOT same object
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        assertNotSame(str1, str2);
    }

    @Test
    void testAssertArrayEquals() {
        // compares arrays element by element
        assertArrayEquals(new int[]{1, 2, 3}, junitExampleClass.getNumbers());
    }

    @Test
    void testAssertThrows() {
        // verifies exception is thrown
        assertThrows(ArithmeticException.class, () -> {
            junitExampleClass.divide(10, 0);
        });
    }

    @Test
    void testAssertDoesNotThrow() {
        // verifies NO exception is thrown
        assertDoesNotThrow(() -> junitExampleClass.divide(10, 2));
    }

    // Disabled test (will not run)
    @Disabled("Example of disabled test")
    @Test
    void disabledTestExample() {
        assertEquals(1, 1);
    }


    //Executes the test multiple times
    //Each row of @CsvSource is one test execution
    @ParameterizedTest  //Same test with multiple inputs
    @CsvSource({
            "2, 3, 6",
            "4, 5, 20",
            "1, 10, 10"
    })
    void testMultiplyParameterized(int a, int b, int expected) {
        // This test will run 3 times with different inputs
        assertEquals(expected, junitExampleClass.multiply(a, b));
    }
}