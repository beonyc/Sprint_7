package org.example;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

public class TestsClass {

    @Test
    @DisplayName("failed test")
    public void someTest(){
        Assert.assertTrue(1==2);
    }

}
