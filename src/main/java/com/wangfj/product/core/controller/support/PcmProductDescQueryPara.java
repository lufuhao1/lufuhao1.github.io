package com.wangfj.product.core.controller.support;

/**
 * Created by wangxuan on 2016-02-16 0016.
 */
public class PcmProductDescQueryPara {

	private Long sid;

	private String color;// 色系

	private String productSid;// 产品编码

	private Integer currentPage;

	private Integer pageSize;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getProductSid() {
		return productSid;
	}

	public void setProductSid(String productSid) {
		this.productSid = productSid;
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
		return "PcmProductDescQueryPara{" + "sid=" + sid + ", color='" + color + '\''
				+ ", productSid='" + productSid + '\'' + ", currentPage=" + currentPage
				+ ", pageSize=" + pageSize + '}';
	}
}
