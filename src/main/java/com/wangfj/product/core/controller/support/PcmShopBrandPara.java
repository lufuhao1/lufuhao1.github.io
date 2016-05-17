package com.wangfj.product.core.controller.support;

public class PcmShopBrandPara {

	private String parentSid;// 集团品牌的sid

	private String shopSid;// 门店的sid

	private String shopCode;// 门店编码

	private Integer currentPage;

	private Integer pageSize;

	public String getParentSid() {
		return parentSid;
	}

	public void setParentSid(String parentSid) {
		this.parentSid = parentSid == null ? null : parentSid.trim();
	}

	public String getShopSid() {
		return shopSid;
	}

	public void setShopSid(String shopSid) {
		this.shopSid = shopSid == null ? null : shopSid.trim();
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode == null ? null : shopCode.trim();
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
		return "PcmShopBrandPara [parentSid=" + parentSid + ", shopSid=" + shopSid + ", shopCode="
				+ shopCode + ", currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
	}

}
