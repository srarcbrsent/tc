package com.ysu.zyw.tc.components.commons.utils;

import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.constant.TcConstant;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.LinkedHashMap;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@UtilityClass
public class TcPinyinUtils {

    public static LinkedHashMap<Character, String[]> toPinyin(String chinese) {
        return chinese.replace(TcConstant.Str.BLANK, TcConstant.Str.EMPTY)
                .chars()
                .collect(Maps::newLinkedHashMap,
                        (rst, ele) -> rst.put((char) ele, toPinyin((char) ele)),
                        LinkedHashMap::putAll);
    }

    @SneakyThrows
    public static String[] toPinyin(char ch) {
        checkArgument(new String(new char[]{ch}).matches("[\u4e00-\u9fa5]"));

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        return PinyinHelper.toHanyuPinyinStringArray(ch, format);
    }

}
