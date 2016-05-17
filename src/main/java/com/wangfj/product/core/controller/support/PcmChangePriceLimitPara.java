package com.wangfj.product.core.controller.support;


public class PcmChangePriceLimitPara {
	
	private long upper;//上限阀值
	
	private long lower;//下限阀值
	
	private String shopName;//门店名
	
	private String shopCode;//门店编号
	
	private Integer currentPage;// 当前页数

	private Integer pageSize;// 每页大小

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

	public long getUpper() {
		return upper;
	}

	public void setUpper(long upper) {
		this.upper = upper;
	}

	public long getLower() {
		return lower;
	}

	public void setLower(long lower) {
		this.lower = lower;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
}
