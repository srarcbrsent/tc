package com.ysu.zyw.tc.demos.bin;

public class TcDemoBin {

    public static void main(String[] args) {
        hexadecimal10To26(26);
        System.out.println();
        hexadecimal10To26(52);
        System.out.println();
        hexadecimal10To26(1);
        System.out.println();
        hexadecimal10To26(22222);
    }

    private static void hexadecimal10To26(int num) {
        if (num > 0) {
            hexadecimal10To26(num / 26);
            System.out.print((char) ('A' + num % 26));
        }
    }

}
