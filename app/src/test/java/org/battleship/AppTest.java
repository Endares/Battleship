/*
 * This source file was generated by the Gradle 'init' task
 */
package org.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class AppTest {
//    @Disabled
//    @Test
//    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
//    // Ensures that System.out is not modified by other tests while this test runs.
//    void test_main_V1_Awins() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//
//        // get an InputStream for our input.txt file
//        // getClass().getClassLoader() is Java's ClassLoader,
//        // responsible for loading classes and resource files (e.g., output.txt) at runtime.
//        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
//        assertNotNull(input);
//
//        // get an InputStream for output.txt
//        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
//        assertNotNull(expectedStream);
//
//        // remember the current System.in and System.out:
//        InputStream oldIn = System.in;
//        PrintStream oldOut = System.out;
//
//        // change to our new input (from "input.txt") and output (our PrintStream that writes
//        // into bytes), and run our App.main.  We'll do this inside a try...finally to ensure
//        // we restore System.in and System.out:
//        try {
//            System.setIn(input);
//            System.setOut(out);
//            App.main(new String[0]);
//            // Notice that we pass new String[0] to main---we don't need any arguments, so we'll just pass
//            // a 0 element String[] (which is legal in Java).
//        }
//        finally {
//            System.setIn(oldIn);
//            System.setOut(oldOut);
//        }
//
//        // read all the data from our expectedStream (output.txt):
//        String expected = new String(expectedStream.readAllBytes());
//        String actual = bytes.toString();
//        assertEquals(expected, actual);
//    }
//
//    @Disabled
//    @Test
//    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
//        // Ensures that System.out is not modified by other tests while this test runs.
//    void test_main_V1_Bwins() throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//
//        // get an InputStream for our input.txt file
//        // getClass().getClassLoader() is Java's ClassLoader,
//        // responsible for loading classes and resource files (e.g., output.txt) at runtime.
//        InputStream input = getClass().getClassLoader().getResourceAsStream("input2.txt");
//        assertNotNull(input);
//
//        // get an InputStream for output.txt
//        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output2.txt");
//        assertNotNull(expectedStream);
//
//        // remember the current System.in and System.out:
//        InputStream oldIn = System.in;
//        PrintStream oldOut = System.out;
//
//        // change to our new input (from "input.txt") and output (our PrintStream that writes
//        // into bytes), and run our App.main.  We'll do this inside a try...finally to ensure
//        // we restore System.in and System.out:
//        try {
//            System.setIn(input);
//            System.setOut(out);
//            App.main(new String[0]);
//            // Notice that we pass new String[0] to main---we don't need any arguments, so we'll just pass
//            // a 0 element String[] (which is legal in Java).
//        }
//        finally {
//            System.setIn(oldIn);
//            System.setOut(oldOut);
//        }
//
//        // read all the data from our expectedStream (output.txt):
//        String expected = new String(expectedStream.readAllBytes());
//        String actual = bytes.toString();
//        assertEquals(expected, actual);
//    }

    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
        // Ensures that System.out is not modified by other tests while this test runs.
    void test_main_V2_Bwins() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);

        // get an InputStream for our input.txt file
        // getClass().getClassLoader() is Java's ClassLoader,
        // responsible for loading classes and resource files (e.g., output.txt) at runtime.
        InputStream input = getClass().getClassLoader().getResourceAsStream("input3.txt");
        assertNotNull(input);

        // get an InputStream for output.txt
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output3.txt");
        assertNotNull(expectedStream);

        // remember the current System.in and System.out:
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        // change to our new input (from "input.txt") and output (our PrintStream that writes
        // into bytes), and run our App.main.  We'll do this inside a try...finally to ensure
        // we restore System.in and System.out:
        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
            // Notice that we pass new String[0] to main---we don't need any arguments, so we'll just pass
            // a 0 element String[] (which is legal in Java).
        }
        finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }

        // read all the data from our expectedStream (output.txt):
        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }
}
