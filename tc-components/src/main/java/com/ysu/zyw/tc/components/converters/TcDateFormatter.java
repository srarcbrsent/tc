package com.ysu.zyw.tc.components.converters;

import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class TcDateFormatter implements Formatter<Date> {

    @Getter
    @Setter
    private String dateFormat;

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return TcDateUtils.parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return TcDateUtils.format(date, dateFormat);
    }

}
