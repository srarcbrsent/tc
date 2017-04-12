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
        System.out.println();
        reverse("i am a boy");
        System.out.println();
        reverse(" boy am i");
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

}
