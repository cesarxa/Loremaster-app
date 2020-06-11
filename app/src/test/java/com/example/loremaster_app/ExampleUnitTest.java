package com.example.loremaster_app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}

public class ProfileUnitTest {
    @Test
    public void readUsername() {
        // creating fake profile
        Profile TestBoy;
        // checking default values
        assertTrue(TestBoy.getUsername() == "");
        System.out.println("Failed on line 25");
        // checking setters and getters
        TestBoy.setUsername("testboy");
        assertTrue(TestBoy.getUsername() == "testboy");
    }

    public void readPassword() {
        // creating fake profile
        Profile TestBoy;
        // checking default values
        assertTrue(TestBoy.password == "");
        // checking setters and getters
        TestBoy.setPassword("testboy");
        assertTrue(TestBoy.password == "testboy");
        // checking blank password
        TestBoy.setPassword("");
        assertTrue(TestBoy.password != "");
    }
}