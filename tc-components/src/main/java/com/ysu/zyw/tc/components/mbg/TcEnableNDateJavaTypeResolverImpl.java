package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

public class TcEnableNDateJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    // Types.DATE; Types.TIME; Types.TIMESTAMP;

    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column,
                                                         FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer = super.overrideDefaultType(column, defaultType);

        switch (column.getJdbcType()) {
            case Types.DATE:
                answer = calculateLocalDateReplacement(column, defaultType);
                break;
            case Types.TIME:
                answer = calculateLocalTimeReplacement(column, defaultType);
                break;
            case Types.TIMESTAMP:
                answer = calculateLocalDateTimeReplacement(column, defaultType);
                break;
        }

        return answer;
    }

    protected FullyQualifiedJavaType calculateLocalDateReplacement(IntrospectedColumn column,
                                                                   FullyQualifiedJavaType defaultType) {
        return new FullyQualifiedJavaType("java.time.LocalDate");
    }

    protected FullyQualifiedJavaType calculateLocalTimeReplacement(IntrospectedColumn column,
                                                                   FullyQualifiedJavaType defaultType) {
        return new FullyQualifiedJavaType("java.time.LocalTime");
    }

    protected FullyQualifiedJavaType calculateLocalDateTimeReplacement(IntrospectedColumn column,
                                                                       FullyQualifiedJavaType defaultType) {
        return new FullyQualifiedJavaType("java.time.LocalDateTime");
    }

}
