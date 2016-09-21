package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TcSelectPrimaryKeyPlugin is a extension of mybatis-generator, plug this plugin will make mybatis-generator generated
 * a method named selectKeyByExample, and this method only return the primary key of table, and this select method
 * will auto have pagination clause, and you could use this clause if TcPaginationPlugin plug in.
 *
 * @author yaowu.zhang
 */
public class TcSelectPrimaryKeyPlugin extends PluginAdapter {

    private static final String SELECT_KEY_BY_EXAMPLE = "selectPrimaryKeyByExample";

    private static final String PRIMARY_KEY_RESULT_MAP = "PrimaryKeyResultMap";

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable
            introspectedTable) {
        generateJavaClient(interfaze, introspectedTable);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        generateSqlMap(document, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    protected boolean hasOnlyOnePrimaryKeyColumn(IntrospectedTable introspectedTable) {
        return introspectedTable.getPrimaryKeyColumns().size() == 1;
    }

    protected void generateJavaClient(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = new Method(SELECT_KEY_BY_EXAMPLE);
        FullyQualifiedJavaType exampleParamType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        method.addParameter(new Parameter(exampleParamType, "example"));

        String primaryKeyJavaType = hasOnlyOnePrimaryKeyColumn(introspectedTable) ? introspectedTable
                .getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType()
                .getFullyQualifiedName() : introspectedTable.getPrimaryKeyType();

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.List<" + primaryKeyJavaType + ">");
        method.setReturnType(returnType);
        interfaze.addMethod(method);
    }

    protected void generateSqlMap(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");

        // id
        xmlElement.addAttribute(new Attribute("id", SELECT_KEY_BY_EXAMPLE));

        // parameter type
        xmlElement.addAttribute(new Attribute("parameterType", introspectedTable.getExampleType()));

        // result map
        if (hasOnlyOnePrimaryKeyColumn(introspectedTable)) {
            String primaryKeyJavaType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType()
                    .getFullyQualifiedName();
            xmlElement.addAttribute(new Attribute("resultType", primaryKeyJavaType));
        } else {
            generatePrimaryKeyBaseMap(document, introspectedTable);
            xmlElement.addAttribute(new Attribute("resultMap", PRIMARY_KEY_RESULT_MAP));
        }

        // content
        String primaryKeyColumnNames = String.join(", ", introspectedTable.getPrimaryKeyColumns().stream().map
                (IntrospectedColumn::getActualColumnName).collect(Collectors.toList()));
        String content =
                "select\n" +
                        "    <if test=\"distinct\">\n" +
                        "      distinct\n" +
                        "    </if>\n" +
                        "    " + primaryKeyColumnNames + "\n" +
                        "    from " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + "\n" +
                        "    <if test=\"_parameter != null\">\n" +
                        "      <include refid=\"" + introspectedTable.getExampleWhereClauseId() + "\" />\n" +
                        "    </if>\n" +
                        "    <if test=\"orderByClause != null\">\n" +
                        "      order by ${orderByClause}\n" +
                        "    </if>\n" +
                        "    <if test=\"" + TcPaginationPlugin.START_LINE + " != null and " + TcPaginationPlugin
                        .PAGE_SIZE + " != null and " + TcPaginationPlugin.PAGE_SIZE + " > 0\">\n" +
                        "      limit #{" + TcPaginationPlugin.START_LINE + "}, #{" + TcPaginationPlugin.PAGE_SIZE +
                        "}\n" +
                        "    </if>";
        xmlElement.addElement(new TextElement(content));

        document.getRootElement().addElement(xmlElement);
    }

    protected void generatePrimaryKeyBaseMap(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("resultMap");

        // id
        xmlElement.addAttribute(new Attribute("id", PRIMARY_KEY_RESULT_MAP));

        // type
        xmlElement.addAttribute(new Attribute("type", introspectedTable.getPrimaryKeyType()));

        // ids
        for (int i = 0; i < introspectedTable.getPrimaryKeyColumns().size(); i++) {
            IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get(i);
            XmlElement idXmlElement = new XmlElement("id");
            idXmlElement.addAttribute(new Attribute("column", introspectedColumn.getActualColumnName()));
            idXmlElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));
            idXmlElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty()));
            xmlElement.addElement(i, idXmlElement);
        }

        // content
        document.getRootElement().addElement(1, xmlElement);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
