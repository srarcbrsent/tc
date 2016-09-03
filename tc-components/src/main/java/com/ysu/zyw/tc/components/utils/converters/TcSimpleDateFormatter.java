package com.ysu.zyw.tc.components.utils.converters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TcSimpleDateFormatter implements Formatter<Date> {

    @Getter
    @Setter
    private SimpleDateFormat simpleDateFormat;

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return simpleDateFormat.parse(text);
    }

    @Override
    public String print(Date object, Locale locale) {
        return simpleDateFormat.format(object);
    }

}
