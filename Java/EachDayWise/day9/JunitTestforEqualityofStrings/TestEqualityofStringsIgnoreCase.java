import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEqualityofStringsIgnoreCase {

    @BeforeAll
    static void runBeforeEverything(){
        System.out.println("before everything but once");
    }

    @Test
    @Order(1)
    void checkStringEquality() {
        assertEquals("Given Strings contain same data",EqualityofStringsIgnoreCase.checkStringEquality("hello","hello"));
    }

    @Test
    @Order(2)
    void checkStringEqualityAgain() {
        assertNotEquals("Given Strings contain same data",EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello"));
    }

    @AfterAll
    static void runAfterEverything(){
        System.out.println("Run after everything but once");
    }

    @BeforeEach
    void runBeforeEach(){
        System.out.println("Run before each test");
    }

    @AfterEach
    void runAfterEach(){
        System.out.println("run after each test");
    }

    @Test
    @Disabled
    @DisplayName("test case with name")
    void withName(){
        System.out.println("Test with name");
    }

    @Test
    void usingAssertTrue(){
        assertTrue("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","hello")));
    }

    @Test
    void usingAssertFalse(){
        assertFalse("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello")));
    }

    @Test
    void usingAsserAll(){
        assertAll(()->assertTrue("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","hello"))),
                ()-> assertFalse("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello"))),
                ()-> assertNotEquals("Given Strings contain same data",EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello")),
                ()->assertEquals("Given Strings contain same data",EqualityofStringsIgnoreCase.checkStringEquality("hello","hello")));
    }

    @Test
    void usingAssumptions(){
        assumeTrue(1<2);
        assertFalse("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello")));
        assumeFalse(1<2);
        System.out.println("after false assumption");
        assertFalse("Given Strings contain same data".equalsIgnoreCase(EqualityofStringsIgnoreCase.checkStringEquality("hello","H=ello")));
        assumingThat((1>2),()-> System.out.println("after suume that"));
    }


    @RepeatedTest(value = 10)
    void usingAssertThrows(){
//      assertThrows(ArithmeticException.class,()->{throw new IOException("arithmatic exception thrown");});

       ArithmeticException arithmeticException= assertThrows(ArithmeticException.class,()->{throw new ArithmeticException("arithmatic exception thrown again");});
        System.out.println(arithmeticException.getMessage());
    }


}



