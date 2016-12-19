package com.ysu.zyw.tc.components.mbg.plguins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * TcPaginationPlugin is a extension of mybatis-generator, plug this plugin will make mybatis-generator generated xml
 * file have pagination ability, this directly based on mybatis-plugin's plugin adapter.
 *
 * @author yaowu.zhang
 */
public class TcPaginationPlugin extends PluginAdapter {

    static final String START_LINE = "startLine";

    static final String PAGE_SIZE = "pageSize";

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
                .addElement(new TextElement("limit #{" + START_LINE + "}, #{" + PAGE_SIZE + "}"));
        element.addElement(paginationWrapElement);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    protected void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
        CommentGenerator commentGenerator = context.getCommentGenerator();

        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(PrimitiveTypeWrapper.getIntegerInstance());
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        char c = name.charAt(0);
        String camel = Character.toUpperCase(c) + name.substring(1);

        Method setMethod = new Method();
        setMethod.setVisibility(JavaVisibility.PUBLIC);
        setMethod.setName("set" + camel);
        setMethod.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), name));
        setMethod.addBodyLine("this." + name + " = " + name + ";");
        commentGenerator.addGeneralMethodComment(setMethod, introspectedTable);
        topLevelClass.addMethod(setMethod);

        Method getMethod = new Method();
        getMethod.setVisibility(JavaVisibility.PUBLIC);
        getMethod.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
        getMethod.setName("get" + camel);
        getMethod.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(getMethod, introspectedTable);
        topLevelClass.addMethod(getMethod);
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

}
