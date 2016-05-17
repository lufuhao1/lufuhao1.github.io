package com.wangfj.product.core.controller.support;

import com.wangfj.product.core.controller.support.base.para.BasePara;

public class PcmRegionQueryPara extends BasePara {

	/**
	 * @Field long serialVersionUID
	 */
	private static final long serialVersionUID = -4620143525650160939L;

	private Long sid;

	private String regionCode;// 邮编

	private String regionInnerCode;//行政区域编码

	private String regionName;// 区域名称

	private Long parentId;// 所属上级sid

	private Integer regionLevel;// 等级（0 省，1 市，2 区，3 县，4 镇 ）

	private Integer currentPage;

	private Integer pageSize;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
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

	public String getRegionInnerCode() {
		return regionInnerCode;
	}

	public void setRegionInnerCode(String regionInnerCode) {
		this.regionInnerCode = regionInnerCode;
	}

	@Override
	public String toString() {
		return "PcmRegionQueryPara [sid=" + sid + ", regionCode=" + regionCode
				+ ", regionInnerCode=" + regionInnerCode + ", regionName=" + regionName
				+ ", parentId=" + parentId + ", regionLevel=" + regionLevel + ", currentPage="
				+ currentPage + ", pageSize=" + pageSize + "]";
	}

}
