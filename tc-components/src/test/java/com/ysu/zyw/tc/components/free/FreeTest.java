package com.ysu.zyw.tc.components.free;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FreeTest {

    public static void main(String[] args) {
        String s = JSON.toJSONString("123123123",
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        System.out.println("-" + s + "-");

        String s1 = JSON.parseObject(s, new TypeReference<String>() {
        });
        System.out.println(s1);
    }

}
