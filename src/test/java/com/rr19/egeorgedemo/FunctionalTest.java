package com.rr19.egeorgedemo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implement the mean, median, mode and range methods of the Calculator class in Java 8 Functional
 * style with the minimum code necessary to pass the tests.
 */
class FunctionalTest {

    @Test
    void range() {
        assertEquals(14, calculator.range(3, 17, 15, 11, 9));
    }

    @Test
    void mean() {
        assertEquals(12.5, calculator.mean(13, 19, null, 14, 16, 5, 8), 0);
    }

    @Test
    void median() {
        assertEquals(6, calculator.median(7, 11, 6, 2, 5), 0);
        assertEquals(13.5, calculator.median(13, 18, 14, 16, 5, 8), 0);
    }

    @Test
    void mode() {
        assertArrayEquals(new int[]{3}, calculator.mode(5, 2, 3, 6, 4, 1, 3));
        assertArrayEquals(new int[]{3, 5}, calculator.mode(4, 5, 3, 1, 3, 2, 5, 6));
        assertArrayEquals(new int[]{5}, calculator.mode(4, 5, 5, 3, 1, 3, 2, 5, 6));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, calculator.mode(1, 3, 2, 4, 5));
    }

    private class Calculator {

        /**
         * range: the difference between min and max values
         */
        int range(int... input) {
            var stats = IntStream.of(input)
                  .summaryStatistics();
            return stats.getMax() - stats.getMin();
        }

        /**
         *  mean: the average of the numbers
         */
        double mean(Integer... input) {
            return Arrays.stream(input)
                  .filter(Objects::nonNull)
                  .collect(Collectors.averagingInt(Integer::intValue));
        }

        /**
         * median: the middle number in a sorted list
         * ...if there are two middle values, return the average of the two
         */
        double median(int... input) {
            //TODO: better stream way?
            Arrays.sort(input);
            var middleIx = input.length / 2 - 1;
            return input.length % 2 == 0 ?
                  (input[middleIx] + input[middleIx+1])/2.0 :
                  input[middleIx + 1];
        }


        /**
         *  mode: the most frequently occurring number
         */
        int[] mode(int... input) {
            var hist = Arrays.stream(input)
                  .boxed()
                  .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()))
                  .entrySet().stream()
                  .sorted(Map.Entry.comparingByValue())
                  .collect(Collectors.toList());
            var maxCnt = hist.get(hist.size()-1).getValue();

            return hist.stream()
                  .filter(entry -> entry.getValue() == maxCnt)
                  .mapToInt(Map.Entry::getKey)
                  .toArray();
        }
    }

    private final Calculator calculator = new Calculator();
}
