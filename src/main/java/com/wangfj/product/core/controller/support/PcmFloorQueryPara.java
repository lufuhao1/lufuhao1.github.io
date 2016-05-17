package com.wangfj.product.core.controller.support;

/**
 * 查询参数
 * 
 * @Class Name SelectPcmFloorDto
 * @Author wangxuan
 * @Create In 2015-8-25
 */
public class PcmFloorQueryPara {

	private Long sid;

	private Long shopSid; /* 所属门店 */

	private String floorName; /* 楼层名称 */

	private String floorCode; /* 楼层编码 */

	private Integer floorStatus;// 楼层状态 默认为0，可用， 1禁用

	private Integer currentPage = 1;// 当前页数

	private Integer pageSize = 10;// 每页大小

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getShopSid() {
		return shopSid;
	}

	public void setShopSid(Long shopSid) {
		this.shopSid = shopSid;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName == null ? null : floorName.trim();
	}

	public String getFloorCode() {
		return floorCode;
	}

	public void setFloorCode(String floorCode) {
		this.floorCode = floorCode == null ? null : floorCode.trim();
	}

	public Integer getFloorStatus() {
		return floorStatus;
	}

	public void setFloorStatus(Integer floorStatus) {
		this.floorStatus = floorStatus;
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
		return "PcmFloorQueryPara{" + "sid=" + sid + ", shopSid=" + shopSid + ", floorName='"
				+ floorName + '\'' + ", floorCode='" + floorCode + '\'' + ", floorStatus="
				+ floorStatus + ", currentPage=" + currentPage + ", pageSize=" + pageSize + '}';
	}
}