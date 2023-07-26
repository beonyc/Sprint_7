package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JenkinsNewTests {

    @Test
    @DisplayName("jenkinsTest")
    @Description("failed test!")
    public void someFailedTest() {
        int sum = 10;
        assertEquals(5 + 5, sum);
    }
}
