package com.ysu.zyw.tc.components.mbg;

import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TcBatchInsertPlugin is a extension of mybatis-generator, plug this plugin will let mybatis-generator
 * generate a function named batchInsert.
 *
 * @author yaowu.zhang
 */
public class TcBatchInsertPlugin extends PluginAdapter {

    private static final String BATCH_INSERT = "batchInsert";

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

    protected void generateJavaClient(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = new Method(BATCH_INSERT);
        String modelType = CollectionUtils.isEmpty(introspectedTable.getBLOBColumns()) ?
                introspectedTable.getBaseRecordType() : introspectedTable.getRecordWithBLOBsType();
        FullyQualifiedJavaType modelListType = new FullyQualifiedJavaType("java.util.List<" + modelType + ">");
        method.addParameter(new Parameter(modelListType, "records"));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");
        method.setReturnType(returnType);
        interfaze.addMethod(method);
    }

    protected void generateSqlMap(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("insert");

        // id
        xmlElement.addAttribute(new Attribute("id", BATCH_INSERT));

        // parameter type
        xmlElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        String columns = String.join(", \n      ", introspectedTable.getAllColumns().stream().map(IntrospectedColumn
                ::getActualColumnName).collect(Collectors.toList()));

        String contentA = "insert into " + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + " " +
                "(\n" +
                "      " + columns + "\n" +
                "    ) values ";

        XmlElement foreachXmlElement = new XmlElement("foreach");
        foreachXmlElement.addAttribute(new Attribute("collection", "list"));
        foreachXmlElement.addAttribute(new Attribute("item", "item"));
        foreachXmlElement.addAttribute(new Attribute("separator", ", "));

        String contentB = String.join(", \n      ", introspectedTable.getAllColumns().stream().map
                (introspectedColumn2InsertSql()
                ).collect(Collectors.toList()));

        foreachXmlElement.addElement(new TextElement("(" + contentB + ")"));

        xmlElement.addElement(new TextElement(contentA));
        xmlElement.addElement(foreachXmlElement);

        document.getRootElement().addElement(xmlElement);
    }

    protected Function<IntrospectedColumn, String> introspectedColumn2InsertSql() {
        // transform column like 'id' to insert part like '#{items.id,jdbcType=VARCHAR}'
        return introspectedColumn ->
                "#{items." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn
                        .getJdbcTypeName() + "}";
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
