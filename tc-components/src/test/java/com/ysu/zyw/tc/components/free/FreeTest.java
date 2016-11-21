package com.ysu.zyw.tc.components.free;


import java.util.HashMap;
import java.util.Map;

public class FreeTest {

    private static FreeTest example = new FreeTest();

    private static Map<Integer, Boolean> test =
            new HashMap<Integer, Boolean>();

    private FreeTest() {
        test.put(1, true);
    }

    public static FreeTest getInstance() {
        return example;
    }

    public static void main(String[] args) {
        FreeTest.getInstance();
    }

}
