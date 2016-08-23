package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * BatchInsertPlugin is a extension of mybatis-generator, plug this plugin will let mybatis-generator generate two
 * function that batchInsert and batchInsertSelective.
 *
 * @author yaowu.zhang
 */
public class BatchInsertPlugin extends PluginAdapter {

    private static final String BATCH_INSERT = "batchInsert";

    private static final String BATCH_ISNERT_SELECTIVE = "batchInsertSelective";

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return false;
    }

}
