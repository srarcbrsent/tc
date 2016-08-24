package com.ysu.zyw.tc.components.mbg;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.function.Supplier;

/**
 * SelectPrimaryKeyPlugin is a extension of mybatis-generator, plug this plugin will make mybatis-generator generated
 * a method named selectKeyByExample, and this method only return the primary key of table, and this select method
 * will auto have pagination clause, and you could use this clause if PaginationPlugin plug in.
 * <p>
 * this plugin only support one primary key, and if this table has more than one primary key, then this method
 * will not generated.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class SelectPrimaryKeyPlugin extends PluginAdapter {

    private static final String SELECT_KEY_BY_EXAMPLE = "selectPrimaryKeyByExample";

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        doIfOnlyOnePrimaryKey(() -> {
            generateJavaClient(interfaze, introspectedTable);
            return null;
        }, introspectedTable);
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    private void generateJavaClient(Interface interfaze, IntrospectedTable introspectedTable) {
        Method method = new Method(SELECT_KEY_BY_EXAMPLE);
        FullyQualifiedJavaType exampleParamType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        method.addParameter(new Parameter(exampleParamType, "example"));
        String primaryKeyJavaType =
                introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedName();
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.List<" + primaryKeyJavaType + ">");
        method.setReturnType(returnType);
        interfaze.addMethod(method);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        doIfOnlyOnePrimaryKey(() -> {
            generateSqlMap(document, introspectedTable);
            return null;
        }, introspectedTable);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private void generateSqlMap(Document document, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");

        // id
        xmlElement.addAttribute(new Attribute("id", SELECT_KEY_BY_EXAMPLE));

        // parameter type
        xmlElement.addAttribute(new Attribute("parameterType", introspectedTable.getExampleType()));

        // result map
        String primaryKeyJavaType =
                introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedName();
        xmlElement.addAttribute(new Attribute("resultType", primaryKeyJavaType));

        // content
        String primaryKeyColumnName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        String content =
                "select\n" +
                        "    <if test=\"distinct\">\n" +
                        "      distinct\n" +
                        "    </if>\n" +
                        "    " + primaryKeyColumnName + "\n" +
                        "    from config\n" +
                        "    <if test=\"_parameter != null\">\n" +
                        "      <include refid=\"" + introspectedTable.getExampleWhereClauseId() + "\" />\n" +
                        "    </if>\n" +
                        "    <if test=\"orderByClause != null\">\n" +
                        "      order by ${orderByClause}\n" +
                        "    </if>\n" +
                        "    <if test=\"" + PaginationPlugin.START_LINE + " != null and " + PaginationPlugin.PAGE_SIZE + " != null and " + PaginationPlugin.PAGE_SIZE + " > 0\">\n" +
                        "      limit #{" + PaginationPlugin.START_LINE + "}, #{" + PaginationPlugin.PAGE_SIZE + "}\n" +
                        "    </if>";
        xmlElement.addElement(new TextElement(content));

        document.getRootElement().addElement(xmlElement);
    }

    private void doIfOnlyOnePrimaryKey(Supplier<Void> supplier, IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns().size() == 1) {
            supplier.get();
        } else {
            log.info("table [{}] has more than one primary key, selectKeyByExample method generate passed!",
                    introspectedTable.getFullyQualifiedTable().getIntrospectedTableName());
        }
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
