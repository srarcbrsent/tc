package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * PaginationPlugin is a extension of mybatis-generator, plug this plugin will make mybatis-generator generated xml
 * file have pagination ability, this directly based on mybatis-plugin's plugin adapter.
 *
 * @author yaowu.zhang
 */
public class PaginationPlugin extends PluginAdapter {

    private static final String START_LINE = "startLine";

    private static final String PAGE_SIZE = "pageSize";

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit clause
        addLimit(topLevelClass, introspectedTable, START_LINE);
        addLimit(topLevelClass, introspectedTable, PAGE_SIZE);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        XmlElement paginationWrapElement = new XmlElement("if");
        paginationWrapElement.addAttribute(new Attribute("test",
                START_LINE + " != null and " + PAGE_SIZE + " != null and " + PAGE_SIZE + " > 0"));
        paginationWrapElement
                .addElement(new TextElement("limit #{" + START_LINE + "} , #{" + PAGE_SIZE + "}"));
        element.addElement(paginationWrapElement);
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        XmlElement paginationWrapElement = new XmlElement("if");
        paginationWrapElement.addAttribute(new Attribute("test",
                START_LINE + " != null and " + PAGE_SIZE + " != null and " + PAGE_SIZE + " > 0"));
        paginationWrapElement
                .addElement(new TextElement("limit #{" + START_LINE + "} , #{" + PAGE_SIZE + "}"));
        element.addElement(paginationWrapElement);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(PrimitiveTypeWrapper.getIntegerInstance());
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), name));
        method.addBodyLine("this." + name + " = " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

}
