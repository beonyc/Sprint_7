package org.example;

import org.apache.commons.lang3.RandomStringUtils;

public class Main {
    public static void main(String[] args) {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        System.out.println(generatedString);
    }
}