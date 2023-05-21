package com.inpress.weatherservice.service.lowlevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UtilityHelperTest {

    @Test
    @DisplayName("Has a real common object")
    void mostCommonTest1() {
        Integer actualMostCommon = UtilityHelper.mostCommon(List.of(1, 2, 1, 1, 2, 1));
        Assertions.assertEquals(1, actualMostCommon);
    }

    @Test
    @DisplayName("Empty list")
    void mostCommonTest2() {
        Integer actualMostCommon = UtilityHelper.mostCommon(List.of());
        Assertions.assertNull(actualMostCommon);
    }

    @Test
    @DisplayName("List with redundant items")
    void mostCommonTest3() {
        Integer actualMostCommon = UtilityHelper.mostCommon(List.of(1, 1, 1, 1));
        Assertions.assertEquals(1, actualMostCommon);
    }

    @Test
    @DisplayName("List with same different items")
    void mostCommonTest4() {
        Integer actualMostCommon = UtilityHelper.mostCommon(List.of(2, 3, 3, 2));
        Assertions.assertEquals(2, actualMostCommon);
    }
}