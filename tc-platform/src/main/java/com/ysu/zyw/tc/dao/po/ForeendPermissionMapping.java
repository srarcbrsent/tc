package com.ysu.zyw.tc.dao.po;

import java.io.Serializable;
import java.util.Date;

public class ForeendPermissionMapping extends ForeendPermissionMappingKey implements Serializable {
    private String permissionName;

    private boolean visiable;

    private Date createdTimestamp;

    private static final long serialVersionUID = 1L;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean isVisiable() {
        return visiable;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ForeendPermissionMapping other = (ForeendPermissionMapping) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals
                (other.getPermissionId()))
                && (this.getPermissionName() == null ? other.getPermissionName() == null : this.getPermissionName()
                .equals(other.getPermissionName()))
                && (this.isVisiable() == other.isVisiable())
                && (this.getCreatedTimestamp() == null ? other.getCreatedTimestamp() == null : this
                .getCreatedTimestamp().equals(other.getCreatedTimestamp()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        result = prime * result + ((getPermissionName() == null) ? 0 : getPermissionName().hashCode());
        result = prime * result + (isVisiable() ? 1231 : 1237);
        result = prime * result + ((getCreatedTimestamp() == null) ? 0 : getCreatedTimestamp().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permissionName=").append(permissionName);
        sb.append(", visiable=").append(visiable);
        sb.append(", createdTimestamp=").append(createdTimestamp);
        sb.append("]");
        return sb.toString();
    }
}