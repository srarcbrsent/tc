package com.ysu.zyw.tc.components.freemarker.support.shirotags;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.LacksRoleTag}</p>
 */
public class LacksRoleTag extends RoleTag {
    protected boolean showTagBody(String roleName) {
        boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
        return !hasRole;
    }
}
