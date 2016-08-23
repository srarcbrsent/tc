package com.ysu.zyw.tc.components.mbg;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

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
public class SelectPrimaryKeyPlugin extends PluginAdapter {

    private static final String SELECT_KEY_BY_EXAMPLE = "selectKeyByExample";

    @Override
    public boolean validate(List<String> warnings) {
        return false;
    }

}
