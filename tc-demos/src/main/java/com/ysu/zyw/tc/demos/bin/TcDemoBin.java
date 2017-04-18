package com.ysu.zyw.tc.demos.bin;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TcDemoBin {

    public static void main(String[] args) {
        hexadecimal10To26(26);
        System.out.println();
        hexadecimal10To26(52);
        System.out.println();
        hexadecimal10To26(1);
        System.out.println();
        hexadecimal10To26(22222);
        System.out.println();
        reverse("i am a boy");
        System.out.println();
        reverse(" boy am i");
        System.out.println();
        countBrace("(1)(1(00)2(10)13)(3)", 3);
        char wo = '我';
        System.out.println();

        final int ARRAY_LENGTH = 10;
        Integer[] array = new Integer[ARRAY_LENGTH];
        Stream.generate(() -> RandomUtils.nextInt(0, 100))
                .limit(ARRAY_LENGTH)
                .collect(Collectors.toList())
                .toArray(array);

        array = ArrayUtils.add(array, array[1]);

        System.out.println(Arrays.toString(array));

        Arrays.stream(array)
                .collect(Collectors.groupingBy(i -> i))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() == 2)
                .findFirst()
                .ifPresent(entry -> {
                    System.out.println(entry.getKey());
                });


    }

    private static void hexadecimal10To26(int num) {
        if (num > 0) {
            hexadecimal10To26(num / 26);
            System.out.print((char) ('A' + num % 26));
        }
    }

    private static void reverse(String str) {
        StringBuilder sb = new StringBuilder();
        for (int before = str.length() - 1, after = str.length(); before >= 0; before--) {
            if (str.charAt(before) == ' ') {
                sb.append(str.substring(before + 1, after)).append(" ");
                after = before;
            } else if (before == 0) {
                sb.append(str.substring(0, after));
            }
        }
        System.out.println(sb.toString());
    }

    private static void countBrace(String str, int index) {
        Stack<Integer> braceStack = new Stack<>();
        // (1)(2)(3)
        // 012345678
        int braces = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                braceStack.push(i);
                braces++;
            } else if (str.charAt(i) == ')') {
                Integer left = braceStack.pop();
                if (braces == index) {
                    System.out.println(str.substring(left + 1, i));
                    break;
                }
            }
        }
    }

}
