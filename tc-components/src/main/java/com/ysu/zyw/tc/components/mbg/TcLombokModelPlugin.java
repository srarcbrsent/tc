package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * TcLombokModelPlugin will use the lombok annotation '@Data' '@Accessors(chain = true)'
 * '@NoArgsConstructor' '@AllArgsConstructor' to the generated model
 *
 * @author yaowu.zhang
 */
public class TcLombokModelPlugin extends PluginAdapter {

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        // generate annotation
        topLevelClass.addAnnotation("@lombok.Data");
        topLevelClass.addAnnotation("@lombok.experimental.Accessors(chain = true)");
        topLevelClass.addAnnotation("@lombok.NoArgsConstructor");
        topLevelClass.addAnnotation("@lombok.AllArgsConstructor");
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        // no getter
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        // no setter
        return false;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
