package com.wangfj.product.core.controller.support;

/**
 * Created by wangxuan on 2016-08-29 0029.
 */
public class PcmStoreInfoQueryPara {

    private String parentSid; /* 所属上级sid */

    private Long groupSid;// 所属集团sid

    private String organizationName; /* 机构名称 */

    private String organizationCode; /* 机构编码 */

    private Integer organizationType; /* 机构类别 */

    private Integer storeType;/* 门店类型 */

    private Integer organizationStatus; /* 机构状态 0.可用；1禁用（默认0） */

    private Integer currentPage;// 当前页数

    private Integer pageSize;// 每页大小

    public String getParentSid() {
        return parentSid;
    }

    public void setParentSid(String parentSid) {
        this.parentSid = parentSid;
    }

    public Long getGroupSid() {
        return groupSid;
    }

    public void setGroupSid(Long groupSid) {
        this.groupSid = groupSid;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Integer organizationType) {
        this.organizationType = organizationType;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Integer getOrganizationStatus() {
        return organizationStatus;
    }

    public void setOrganizationStatus(Integer organizationStatus) {
        this.organizationStatus = organizationStatus;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PcmStoreInfoQueryPara{" +
                "parentSid='" + parentSid + '\'' +
                ", groupSid=" + groupSid +
                ", organizationName='" + organizationName + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationType=" + organizationType +
                ", storeType=" + storeType +
                ", organizationStatus=" + organizationStatus +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
