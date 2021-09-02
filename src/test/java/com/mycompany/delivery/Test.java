package com.mycompany.delivery;

public class Test {
    @org.junit.Test
    public void test() {
        String USERNAME_REGEX = "^[a-zA-Z0-9_]{4,19}$";
        System.out.println(USERNAME_REGEX.matches("user123"));
    }
}
